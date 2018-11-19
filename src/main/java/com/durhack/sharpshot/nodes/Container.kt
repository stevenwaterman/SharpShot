package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Coordinate
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode
import com.durhack.sharpshot.nodes.io.AbstractInputNode
import com.durhack.sharpshot.nodes.io.InNode
import com.durhack.sharpshot.util.Listeners
import com.durhack.sharpshot.util.Movement

import java.math.BigInteger
import java.util.*

class Container(val width: Int, val height: Int) {
    val completionListeners = Listeners()

    val nodes = HashMap<Coordinate, INode>()
    val bullets = HashMap<Coordinate, Bullet>()

    fun start(input: List<BigInteger>) {
        val newBullets = HashMap<Coordinate, Bullet>()

        // Input nodes spawn 0 if their index == 0
        for (coordinate in nodes.keys) {
            val node = nodes[coordinate]
            if (node is AbstractInputNode) {
                val bulletParams = node.input(input)

                for ((_, value) in bulletParams) {
                    val newBullet = Bullet(node.rotation, value)
                    val newCoordinate = coordinate.plus(newBullet.direction)
                    newBullets[newCoordinate] = newBullet
                }
            }
        }

        bullets.putAll(newBullets)
    }

    fun firstAvailableInputIndex(): Int {
        val inputs = nodes.values
                .filter { node -> node is InNode }
                .map { node -> (node as InNode).index }

        var counter = 1
        while (inputs.contains(counter)) {
            counter++
        }
        return counter
    }

    /**
     * Bullets on top of node -> store
     * Other bullets -> tick
     * stored bullets -> process and output
     * all bullets -> check
     */
    fun tick() {
        var haltNodeHit = false

        val bulletMovements = mutableListOf<Pair<Movement, Bullet>>()

        //Bullets on nodes
        val captured = bullets.mapNotNull { (coord, bullet) ->
            val node = nodes[coord] ?: return@mapNotNull null
            return@mapNotNull Triple(coord, node, bullet)
        }.toList()


        //Bullets not on nodes
        val capturedBullets = captured.map { it.third }.toSet()
        val freeBullets = bullets.filterValues { it !in capturedBullets }

        //Generate movements for free bullets
        bulletMovements.addAll(freeBullets.map {(coord, bullet) ->
            val newCoord = coord.plus(bullet.direction).wrap(width, height)
            return@map Movement(coord, newCoord) to bullet
        })

        //Update captured bullets
        captured.forEach {(coord, node, bullet) ->
            // special case
            // if any halt nodes get hit by a bullet, halt at end of
            if (!haltNodeHit && node is HaltNode) {
                haltNodeHit = true
            }

            //TODO this brings great shame onto my family
            var bulletDirection = bullet.direction
            var dir = Direction.UP
            while (dir != node.rotation) {
                dir = dir.clockwise()
                bulletDirection = bulletDirection.antiClockwise()
            }
            val rotatedBullet = Bullet(bulletDirection, bullet.value)

            node.run(rotatedBullet).forEach { direction, value ->
                //TODO this too
                bulletDirection = direction
                dir = Direction.UP
                while (dir != node.rotation) {
                    dir = dir.clockwise()
                    bulletDirection = bulletDirection.clockwise()
                }

                val newBullet = Bullet(bulletDirection, value)
                val newCoordinate = coord.plus(newBullet.direction).wrap(width, height)
                val movement = Movement(coord, newCoordinate)
                bulletMovements.add(movement to newBullet)
            }
        }

        //Remove swapping bullets
        //When two bullets are adjacent and each is trying to move into the space the other is occupying, a naive solution
        //would allow them to swap without colliding.
        //This solves that problem
        val swapProcessed = mutableSetOf<Movement>()
        val swapDelete = mutableSetOf<Coordinate>()
        bulletMovements.forEach { (movement, _) ->
            val from = movement.from
            val to = movement.to
            val foundSwaps = swapProcessed.stream().anyMatch { other -> from == other.to && to == other.from }
            if (foundSwaps) {
                swapDelete.add(from)
                swapDelete.add(to)
            }
            swapProcessed.add(movement)
        }
        bulletMovements.removeIf { (movement, _) -> swapDelete.contains(movement.from) || swapDelete.contains(movement.to) }

        //Remove bullets that end in the same place
        val collideProcessed = mutableSetOf<Movement>()
        val collideDelete = mutableSetOf<Coordinate>()
        bulletMovements.forEach { (movement, _) ->
            val to = movement.to
            val foundSameFinal = collideProcessed.stream().anyMatch { other -> to == other.to }
            if (foundSameFinal) {
                collideDelete.add(to)
            }
            collideProcessed.add(movement)
        }
        bulletMovements.removeIf { (movement, _) -> collideDelete.contains(movement.to) }

        //Move bullets
        val newBullets = bulletMovements.map { (movement, bullet) ->
            movement.to to bullet
        }.toMap()

        bullets.clear()
        bullets.putAll(newBullets)

        if (haltNodeHit) {
            halt()
        }
    }

    private fun halt() {
        completionListeners()
        reset()
    }

    fun reset() {
        bullets.clear()
        nodes.forEach { _, node -> node.reset() }
    }

    fun clearAll() {
        bullets.clear()
        nodes.clear()
    }
}
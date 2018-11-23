package com.durhack.sharpshot.logic

import com.durhack.sharpshot.nodes.INode
import com.durhack.sharpshot.nodes.io.AbstractInputNode
import com.durhack.sharpshot.nodes.other.HaltNode
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.observable
import java.math.BigInteger

class Container(val width: Int, val height: Int) {
    val running = SimpleBooleanProperty()

    val nodes = mutableMapOf<Coordinate, INode>().observable()
    val bullets = mutableMapOf<Coordinate, Bullet>().observable()

    var halt = false

    fun start(input: List<BigInteger?>) {
        bullets.putAll(nodes.keys.flatMap { coordinate ->
            val node = nodes[coordinate] as? AbstractInputNode ?: return@flatMap listOf<Pair<Coordinate, Bullet>>()
            node.input(input).map { (_, value) ->
                coordinate to Bullet(node.rotation, value)
            }
        }.toMap())

        running.set(true)
    }

    /**
     * Bullets on top of node -> store
     * Other bullets -> tick
     * stored bullets -> process and output
     * all bullets -> check
     */
    fun tick() {
        val movements = moveBullets().toMutableList()

        //Remove swapping bullets
        //When two bullets are adjacent and each is trying to move into the space the other is occupying, a naive solution
        //would allow them to swap without colliding.
        //This solves that problem
        val swapProcessed = mutableSetOf<SwapChecker>()
        val swapDelete = mutableSetOf<Coordinate>()
        movements.map { (movement, _) ->
            SwapChecker(movement)
        }.forEach { swap ->
            if (swap.c1 !in swapDelete) {
                if (swap in swapProcessed) {
                    swapProcessed.remove(swap)
                    swapDelete.add(swap.c1)
                    swapDelete.add(swap.c2)
                }
                else {
                    swapProcessed.add(swap)
                }
            }
        }

        movements.removeIf { (movement, _) ->
            swapDelete.contains(movement.from)
        }

        //Remove bullets that end in the same place
        val collideProcessed = mutableSetOf<Coordinate>()
        val collideDelete = mutableSetOf<Coordinate>()
        movements.forEach { (movement, _) ->
            val to = movement.to
            if (to !in collideDelete) {
                if (to in collideProcessed) {
                    collideProcessed.remove(to)
                    collideDelete.add(to)
                }
                else {
                    collideProcessed.add(to)
                }
            }
        }
        movements.removeIf { (movement, _) -> collideDelete.contains(movement.to) }

        //Move bullets
        val newBullets = movements.map { (movement, bullet) ->
            movement.to to bullet
        }.toMap()

        //replace old bullets with new bullets
        bullets.clear()
        bullets.putAll(newBullets)

        //Halt if we hit a halt node
        if (halt) {
            reset()
        }
    }

    private fun moveBullets(): List<Pair<Movement, Bullet>> {
        //Bullets on nodes
        val captured = bullets.mapNotNull { (coord, bullet) ->
            val node = nodes[coord] ?: return@mapNotNull null
            return@mapNotNull Triple(coord, node, bullet)
        }

        val freeBullets: Map<Coordinate, Bullet> = (bullets - captured.map { it.first })

        return moveBullets(freeBullets) + processBullets(captured)
    }

    private fun processBullets(captured: List<Triple<Coordinate, INode, Bullet>>) =
            captured.flatMap { (coord, node, bullet) ->
                // special case
                // if any halt nodes get hit freeBulletLocations.add(by a bullet, halt at end of
                if (!halt && node is HaltNode) {
                    halt = true
                }

                val rotatedDirection = bullet.direction.plusQuarters(-node.rotation.quarters)
                val rotatedBullet = Bullet(rotatedDirection, bullet.value)

                return@flatMap node.run(rotatedBullet).map { (direction, value) ->
                    val unrotatedDirection = direction.plusQuarters(node.rotation.quarters)
                    val newBullet = Bullet(unrotatedDirection, value)

                    val newCoordinate = coord.plus(newBullet.direction).wrap(width, height)
                    val movement = Movement(coord, newCoordinate)

                    return@map movement to newBullet
                }
            }

    private fun moveBullets(bullets: Map<Coordinate, Bullet>) = bullets.map { (coord, bullet) ->
        val newCoord = coord.plus(bullet.direction).wrap(width, height)
        return@map Movement(coord, newCoord) to bullet
    }

    fun reset() {
        bullets.clear()
        nodes.forEach { _, node -> node.reset() }
        running.set(false)
    }

    fun clearAll() {
        reset()
        nodes.clear()
        running.set(false)
    }
}
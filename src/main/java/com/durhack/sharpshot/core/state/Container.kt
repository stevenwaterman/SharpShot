package com.durhack.sharpshot.core.state

import com.durhack.sharpshot.core.control.CollisionReport
import com.durhack.sharpshot.core.control.TickReport
import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.input.AbstractInputNode
import com.durhack.sharpshot.core.nodes.other.HaltNode
import com.durhack.sharpshot.core.state.tick.*
import com.durhack.sharpshot.gui.container.Extract
import com.durhack.sharpshot.gui.util.ui
import com.durhack.sharpshot.util.IdiotProgrammerException
import com.durhack.sharpshot.util.filterType
import com.durhack.sharpshot.util.pairDuplicates
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*
import java.math.BigInteger

class Container(initWidth: Int, initHeight: Int) {
    private val innerWidthProp = SimpleIntegerProperty(initWidth)
    val widthProp = innerWidthProp.ui()
    var width by innerWidthProp

    private val innerHeightProp = SimpleIntegerProperty(initHeight)
    val heightProp = innerHeightProp.ui()
    var height: Int by innerHeightProp

    private val innerRunningProp = SimpleBooleanProperty(false)
    val runningProp = innerRunningProp.ui()
    var running by innerRunningProp
        private set

    val nodes = mutableMapOf<Coordinate, AbstractNode>()
    val bullets = mutableListOf<Bullet>()

    fun clear() {
        nodes.clear()
        bullets.clear()
        running = false
    }

    fun reset() {
        nodes.values.forEach(AbstractNode::reset)
        bullets.clear()
        running = false
    }

    fun start(input: List<BigInteger?>) {
        val inputNodes = nodes.filterType<Coordinate, AbstractInputNode>()
        inputNodes.forEach { (coord, node) ->
            val (dir, value) = node.initialise(input) ?: return@forEach
            val bullet = createBulletFromNode(coord, dir, value)
            bullets.add(bullet)
        }
        running = true
    }

    private fun createBulletFromNode(coordinate: Coordinate, relativeDirection: Direction, value: BigInteger?): Bullet {
        val node = nodes[coordinate] ?: throw IdiotProgrammerException("There is no node at coordinate $coordinate")
        val absDirection = node.direction + relativeDirection
        return Bullet(coordinate, absDirection, value)
    }

    private fun createBulletsFromNode(coordinate: Coordinate, data: Map<Direction, BigInteger?>) =
            data.map { (relativeDirection, value) ->
                createBulletFromNode(coordinate, relativeDirection, value)
            }

    /**
     * Bullets on top of node -> store
     * Other bullets -> tick
     * stored bullets -> process and output
     * all bullets -> check
     */
    fun tick(): TickReport {
        var halted = tryHalt()
        processBullets()
        val movements = bulletMovement()
        val collisionReport = collide(movements)
        clearCollidedBullets(collisionReport)
        moveBullets()

        val outputs = readOutputs()
        halted = halted || bullets.isEmpty()
        if (halted) reset()
        return TickReport(collisionReport, outputs, halted)
    }

    private fun tryHalt() = bullets
            .mapNotNull { nodes[it.coordinate] }
            .filterIsInstance<HaltNode>()
            .any()

    /**
     * Generate the list of movements
     */
    private fun bulletMovement(): Set<BulletMovement> =
            bullets.map { bullet ->
                val coord = bullet.coordinate
                val nextCoord = bullet.nextCoord()
                val movement = Movement(coord, nextCoord)
                return@map BulletMovement(bullet, movement)
            }.toSet()

    /**
     * Partition movements based on whether they cause a collision
     */
    private fun collide(movements: Set<BulletMovement>): CollisionReport {
        val remaining = movements.toMutableSet()
        val swapCollisions = swapCollisions(movements)

        val swapRemove = swapCollisions.map(Collision::a) + swapCollisions.map(Collision::b)
        remaining.removeAll(swapRemove)

        val finalCollisions = finalCollisions(remaining)

        val finalRemove = finalCollisions.map(Collision::a) + finalCollisions.map(
                Collision::b)
        remaining.removeAll(finalRemove)

        return CollisionReport(swapCollisions, finalCollisions, remaining)
    }

    /**
     * Given a list of movements (s1,t1), (s2,t2), (s3,t3)...
     * Find any pairs where (si == tj and sj == ti)
     * I.e. pairs where one is the inverse of the other
     * Return only i for each pair i,j
     */
    private fun swapCollisions(movements: Set<BulletMovement>) = baseCollide(movements, ::SwapCheck)

    /**
     * Given a list of movements (s1,t1), (s2,t2), (s3,t3)...
     * Find any pairs where (ti == tj)
     * I.e. pairs where both end in the same square
     * Return both i and j for each pair
     */
    private fun finalCollisions(movements: Set<BulletMovement>) = baseCollide(movements, ::FinalCheck)

    private fun baseCollide(movements: Set<BulletMovement>, checker: (BulletMovement) -> Checker): List<Collision> {
        val checkers = movements.map(checker)
        val pairs = checkers.pairDuplicates()
        val collisions = pairs.map { (a, b) ->
            Collision(a.bulletMovement, b.bulletMovement)
        }
        return collisions
    }

    /**
     * Remove all bullets that collided
     */
    private fun clearCollidedBullets(collisionReport: CollisionReport) {
        bullets.removeAll(collisionReport.bulletsToRemove)
    }

    /**
     * Move all bullets in the direction they are facing
     */
    private fun moveBullets() {
        bullets.replaceAll(Bullet::increment)
    }

    /**
     * Update any bullets that are on a node
     */
    private fun processBullets() {
        val toProcess =
                bullets.mapNotNull { bullet ->
                    val node = nodes[bullet.coordinate] ?: return@mapNotNull null
                    return@mapNotNull bullet to node
                }

        val newBullets = toProcess.flatMap { (bullet, node) ->
            val data = node.process(bullet)
            createBulletsFromNode(bullet.coordinate, data)
        }

        bullets.removeAll(toProcess.map { it.first })
        bullets += newBullets
    }

    private fun readOutputs(): List<BigInteger?> {
        val outputting = bullets.filterNot { isInside(it.coordinate) }
        bullets.removeAll(outputting)
        return outputting.map(Bullet::value)
    }

    fun isInside(coord: Coordinate) = coord.x in (0 until width) && coord.y in (0 until height)

    fun setTo(extract: Extract) {
        clear()
        nodes.putAll(extract.nodes)
        width = extract.width
        height = extract.height
    }
}
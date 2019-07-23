package com.durhack.sharpshot.core.nodes

import com.durhack.sharpshot.core.state.Bullet
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

abstract class AbstractNode(var direction: Direction) {

    fun process(bullet: Bullet): Map<Direction, BigInteger?> {
        val absDir = bullet.direction
        val relDir = absDir - direction
        return process(relDir, bullet.value)
    }

    fun clockwise() {
        direction = direction.clockwise
    }

    fun anticlockwise() {
        direction = direction.antiClockwise
    }

    protected abstract fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?>
    abstract fun reset()

    abstract val type: String

    final override fun toString() = type.split(" ").joinToString(" ") {
        it.first().toUpperCase() + it.drop(1).toLowerCase()
    }
}
package com.durhack.sharpshot.core.nodes

import com.durhack.sharpshot.core.state.Bullet
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

abstract class AbstractNode(val direction: Direction) {

    fun process(bullet: Bullet): Map<Direction, BigInteger?> {
        val absDir = bullet.direction
        val relDir = absDir - direction
        return process(relDir, bullet.value)
    }

    protected abstract fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?>
    abstract fun reset()

    abstract val type: String

    final override fun toString() = type.split(" ").joinToString(" ") {
        it.first().toUpperCase() + it.drop(1).toLowerCase()
    }


    protected abstract fun copyWithDirection(direction: Direction): AbstractNode
    val clockwise: AbstractNode get() = copyWithDirection(direction.clockwise)
    val antiClockwise: AbstractNode get() = copyWithDirection(direction.antiClockwise)
    val mirroredVertical: AbstractNode get() = copyWithDirection(direction.mirroredVertical)
    val mirroredHorizontal: AbstractNode get() = copyWithDirection(direction.mirroredHorizontal)
}
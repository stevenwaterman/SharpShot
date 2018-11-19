package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode
import java.math.BigInteger

abstract class ConditionalNode : INode {
    override var rotation = Direction.UP
        protected set

    override fun rotateClockwise() {
        rotation = Direction.clockwiseOf(rotation)
    }

    // Ignore null bullets
    override fun run(bullet: Bullet): Map<Direction, BigInteger> {
        val value = bullet.value ?: return mapOf()
        return when {
            branch(value) -> mapOf(Direction.UP to value)
            else          -> mapOf(bullet.direction to value)
        }
    }

    protected abstract fun branch(value: BigInteger): Boolean

    override fun reset() {}
}

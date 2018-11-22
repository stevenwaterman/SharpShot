package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.logic.Direction
import com.durhack.sharpshot.nodes.INode
import java.math.BigInteger

abstract class AbstractConditionalNode : INode() {
    // Null bullets never redirect
    override fun run(bullet: Bullet): Map<Direction, BigInteger?> {
        val value = bullet.value ?: return mapOf(bullet.direction to bullet.value)
        return when {
            branch(value) -> mapOf(Direction.UP to value)
            else -> mapOf(bullet.direction to value)
        }
    }

    protected abstract fun branch(value: BigInteger): Boolean

    override fun reset() {}
}

package com.durhack.sharpshot.core.nodes.routing.conditional

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

abstract class AbstractConditionalNode : AbstractNode() {
    /**
     * Null bullets never redirect
     */
    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> {
        return when {
            branch(value) -> mapOf(Direction.UP to value)
            else -> mapOf(relativeDirection to value)
        }
    }

    protected abstract fun branch(value: BigInteger?): Boolean

    override fun reset() {}
}

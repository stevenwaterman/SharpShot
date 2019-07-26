package com.durhack.sharpshot.core.nodes.other

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

class ConstantNode(val value: BigInteger?, direction: Direction) : AbstractNode(direction) {
    /**
     * If both the bullet and the output are in the same direction, the second element in the map overrides the first
     * so the start bullet is destroyed
     */
    override fun process(relativeDirection: Direction, value: BigInteger?) =
            mutableMapOf(relativeDirection to value, Direction.UP to this.value)

    override fun reset() {}

    override val type = "constant"

    override fun copyWithDirection(direction: Direction) = ConstantNode(value, direction)
}

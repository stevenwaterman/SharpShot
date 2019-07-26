package com.durhack.sharpshot.core.nodes.routing.conditional

import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

class IfNullNode(direction: Direction) : AbstractConditionalNode(direction) {
    override fun branch(value: BigInteger?) = value == null
    override val type = "branch if null"
    override fun copyWithDirection(direction: Direction) = IfNullNode(direction)
}

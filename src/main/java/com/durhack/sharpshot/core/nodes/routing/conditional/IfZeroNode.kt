package com.durhack.sharpshot.core.nodes.routing.conditional

import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

class IfZeroNode(direction: Direction) : AbstractConditionalNode(direction) {
    override fun branch(value: BigInteger?) = value?.signum() == 0
    override val type = "branch if zero"
}

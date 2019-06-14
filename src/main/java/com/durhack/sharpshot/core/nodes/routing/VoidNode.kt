package com.durhack.sharpshot.core.nodes.routing

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

class VoidNode(direction: Direction) : AbstractNode(direction) {
    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> = mapOf()
    override fun reset() {}
    override val type = "void"
}

package com.durhack.sharpshot.core.nodes.routing

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

class RotateNode(direction: Direction) : AbstractNode(direction) {
    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> =
            mapOf(relativeDirection.clockwise to value)

    override fun reset() {}
    override val type = "rotate"
}

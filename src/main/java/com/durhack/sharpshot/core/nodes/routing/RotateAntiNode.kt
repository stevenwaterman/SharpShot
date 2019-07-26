package com.durhack.sharpshot.core.nodes.routing

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

class RotateAntiNode(direction: Direction) : AbstractNode(direction) {
    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> =
            mapOf(relativeDirection.antiClockwise to value)

    override fun reset() {}
    override val type = "rotate anti"

    override fun copyWithDirection(direction: Direction) = RotateAntiNode(direction)
}

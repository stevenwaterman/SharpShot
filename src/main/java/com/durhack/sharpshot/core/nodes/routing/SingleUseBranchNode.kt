package com.durhack.sharpshot.core.nodes.routing

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

class SingleUseBranchNode(direction: Direction) : AbstractNode(direction) {

    var enabled = true

    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> {
        return if (enabled) {
            enabled = false
            mapOf(Direction.UP to value)
        }
        else {
            mapOf(relativeDirection to value)
        }
    }

    override fun reset() {
        enabled = true
    }

    override val type = "single use branch"

    override fun copyWithDirection(direction: Direction) = SingleUseBranchNode(direction)
}

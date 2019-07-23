package com.durhack.sharpshot.core.nodes.input

import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

class InputNode(val index: Int?, direction: Direction) : AbstractInputNode(direction) {
    private var input: BigInteger? = null

    override fun initialise(inputs: List<BigInteger?>): Pair<Direction, BigInteger?>? {
        input = if (index == null) null else inputs.getOrNull(index-1)
        return Direction.UP to input
    }

    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> {
        return mapOf(
                relativeDirection to value,
                Direction.UP to input
                    )
    }

    override fun reset() {}

    override val type = "input"
}

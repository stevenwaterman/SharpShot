package com.durhack.sharpshot.core.nodes.input

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

abstract class AbstractInputNode(direction: Direction) : AbstractNode(direction) {
    abstract fun initialise(inputs: List<BigInteger?>): Pair<Direction, BigInteger?>?
}

package com.durhack.sharpshot.core.nodes.input

import com.durhack.sharpshot.core.nodes.INode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

abstract class AbstractInputNode : INode() {
    abstract fun initialise(inputs: List<BigInteger?>): Pair<Direction, BigInteger?>?
}

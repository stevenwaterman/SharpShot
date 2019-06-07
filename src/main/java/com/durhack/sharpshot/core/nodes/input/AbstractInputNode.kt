package com.durhack.sharpshot.core.nodes.input

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

abstract class AbstractInputNode : AbstractNode() {
    abstract fun initialise(inputs: List<BigInteger?>): Pair<Direction, BigInteger?>?
}

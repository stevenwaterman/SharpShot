package com.durhack.sharpshot.nodes.input

import com.durhack.sharpshot.logic.Direction
import com.durhack.sharpshot.nodes.INode

import java.math.BigInteger

abstract class AbstractInputNode : INode() {
    abstract fun input(inputs: List<BigInteger?>): Map<Direction, BigInteger?>
}

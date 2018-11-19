package com.durhack.sharpshot.nodes.io

import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode

import java.math.BigInteger

abstract class AbstractInputNode : INode {
    abstract fun input(inputs: List<BigInteger>): Map<Direction, BigInteger?>
}

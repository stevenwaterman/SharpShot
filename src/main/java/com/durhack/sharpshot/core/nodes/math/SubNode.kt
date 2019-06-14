package com.durhack.sharpshot.core.nodes.math

import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

class SubNode(direction: Direction) : AbstractMathNode(direction) {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? = val1.subtract(val2)
    override val type = "subtract"
}
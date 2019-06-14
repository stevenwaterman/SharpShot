package com.durhack.sharpshot.core.nodes.math

import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

class AddNode(direction: Direction) : AbstractMathNode(direction) {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? = val1.add(val2)
    override val type = "add"
}

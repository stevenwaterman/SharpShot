package com.durhack.sharpshot.core.nodes.math

import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

class DivNode(direction: Direction) : AbstractMathNode(direction) {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? {
        return when (val2) {
            BigInteger.ZERO -> null
            else -> val1.divide(val2)
        }
    }

    override val type = "divide"

    override fun copyWithDirection(direction: Direction) = DivNode(direction)
}

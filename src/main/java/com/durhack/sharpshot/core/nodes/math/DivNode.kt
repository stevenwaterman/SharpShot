package com.durhack.sharpshot.core.nodes.math

import java.math.BigInteger

class DivNode : AbstractMathNode() {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? {
        return when (val2) {
            BigInteger.ZERO -> null
            else -> val1.divide(val2)
        }
    }

    override val type = "divide"
    override val tooltip = "Adds two bullets"
}

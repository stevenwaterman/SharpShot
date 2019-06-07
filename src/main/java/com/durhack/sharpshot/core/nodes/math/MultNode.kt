package com.durhack.sharpshot.core.nodes.math

import java.math.BigInteger

class MultNode : AbstractMathNode() {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? = val1.multiply(val2)
    override val type = "multiply"
    override val tooltip = "Multiplies two numbers"
}

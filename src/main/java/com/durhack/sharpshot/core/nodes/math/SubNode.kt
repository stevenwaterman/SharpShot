package com.durhack.sharpshot.core.nodes.math

import java.math.BigInteger

class SubNode : AbstractMathNode() {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? = val1.subtract(val2)
    override val type = "subtract"
    override val tooltip = "Subtracts the second bullet from the first"
}

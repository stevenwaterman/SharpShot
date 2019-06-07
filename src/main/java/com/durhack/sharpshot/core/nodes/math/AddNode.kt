package com.durhack.sharpshot.core.nodes.math

import java.math.BigInteger

class AddNode : AbstractMathNode() {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? = val1.add(val2)
    override val type = "add"
    override val tooltip = "Adds two bullets"
}

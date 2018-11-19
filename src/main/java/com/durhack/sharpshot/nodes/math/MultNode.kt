package com.durhack.sharpshot.nodes.math

import com.durhack.sharpshot.gui.Triangle
import javafx.scene.Node
import javafx.scene.paint.Color

import java.math.BigInteger

class MultNode : AbstractMathNode() {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? = val1.multiply(val2)
    override val type = "multiply"
    override fun graphic() = Triangle(rotation, Color.web("#8df8e6"), "x")
    override val tooltip = "Multiplies two numbers"
}

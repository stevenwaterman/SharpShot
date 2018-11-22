package com.durhack.sharpshot.nodes.math

import com.durhack.sharpshot.gui.shapes.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger

class DivNode : AbstractMathNode() {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? {
        return when (val2) {
            BigInteger.ZERO -> null
            else -> val1.divide(val2)
        }
    }

    override val type = "divide"
    override fun graphic() = Triangle(rotation, Color.web("#CC66FF"), "÷")
    override val tooltip = "Adds two bullets"
}

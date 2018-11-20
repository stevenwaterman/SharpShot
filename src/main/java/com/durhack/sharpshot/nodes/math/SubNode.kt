package com.durhack.sharpshot.nodes.math

import com.durhack.sharpshot.gui.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger

class SubNode : AbstractMathNode() {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? = val1.subtract(val2)
    override val type = "subtract"
    override fun graphic() = Triangle(rotation, Color.web("#7700FF"), "-")
    override val tooltip = "Subtracts the second bullet from the first"
}

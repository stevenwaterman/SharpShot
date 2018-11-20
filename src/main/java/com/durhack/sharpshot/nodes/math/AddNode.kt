package com.durhack.sharpshot.nodes.math

import com.durhack.sharpshot.gui.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger

class AddNode : AbstractMathNode() {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? = val1.add(val2)
    override fun graphic() = Triangle(rotation, Color.web("#add8e6"), "+")
    override val type = "add"
    override val tooltip = "Adds two bullets"
}

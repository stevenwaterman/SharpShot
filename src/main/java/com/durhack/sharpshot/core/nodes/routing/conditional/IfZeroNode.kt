package com.durhack.sharpshot.core.nodes.routing.conditional

import com.durhack.sharpshot.gui.shapes.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger

class IfZeroNode : AbstractConditionalNode() {
    override fun branch(value: BigInteger?) = value?.signum() == 0
    override fun graphic() = Triangle(direction, Color.web("#FF0000"), "=0")
    override val type = "branch if zero"
    override val tooltip = "Redirects all zero bullets (=0). Other bullets pass through unaffected"
}

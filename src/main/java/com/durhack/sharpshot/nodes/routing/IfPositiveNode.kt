package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.gui.shapes.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger

class IfPositiveNode : AbstractConditionalNode() {
    override fun branch(value: BigInteger) = value.signum() == 1
    override fun graphic() = Triangle(rotation, Color.web("#FF9900"), ">0")
    override val type = "branch if positive"
    override val tooltip = "Redirects all positive bullets (>0). Other bullets pass through unaffected"
}

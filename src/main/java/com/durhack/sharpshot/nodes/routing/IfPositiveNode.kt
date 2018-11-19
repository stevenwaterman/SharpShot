package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.gui.Triangle
import javafx.scene.Node
import javafx.scene.paint.Color
import java.math.BigInteger

class IfPositiveNode : ConditionalNode() {
    override fun branch(value: BigInteger) = value.signum() == 1
    override fun toGraphic() = Triangle(rotation, Color.web("#FF9900"), ">0")
    override fun toString() = "Branch if Positive"
}

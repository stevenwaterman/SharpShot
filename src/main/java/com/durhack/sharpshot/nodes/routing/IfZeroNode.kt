package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.gui.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger

class IfZeroNode : ConditionalNode() {
    override fun branch(value: BigInteger) = value.signum() == 0
    override fun toGraphic() = Triangle(rotation, Color.web("#FF0000"), "=0")
    override fun toString() = "Branch if Zero"
}

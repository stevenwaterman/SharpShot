package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.gui.Triangle
import javafx.scene.Node
import javafx.scene.paint.Color
import java.math.BigInteger

class BranchNode : ConditionalNode() {
    override fun branch(value: BigInteger) = true
    override fun toGraphic() = Triangle(rotation, Color.web("#FF6699"), "")
    override fun toString() = "Branch"
}

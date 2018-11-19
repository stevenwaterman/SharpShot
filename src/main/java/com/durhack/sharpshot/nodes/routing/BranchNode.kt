package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.gui.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger

class BranchNode : AbstractConditionalNode() {
    override fun branch(value: BigInteger) = true
    override fun graphic() = Triangle(rotation, Color.web("#FF6699"), "")
    override fun toString() = "Branch"
    override val tooltip = "Redirects bullets"
}

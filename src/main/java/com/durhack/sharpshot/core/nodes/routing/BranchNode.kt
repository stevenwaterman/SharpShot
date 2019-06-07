package com.durhack.sharpshot.core.nodes.routing

import com.durhack.sharpshot.gui.shapes.Triangle
import com.durhack.sharpshot.core.nodes.INode
import com.durhack.sharpshot.core.state.Direction
import javafx.scene.paint.Color
import java.math.BigInteger

class BranchNode : INode() {

    override fun process(relativeDirection: Direction, value: BigInteger?) =
            mapOf(Direction.UP to value)

    override fun reset() {}
    override fun graphic() = Triangle(direction, Color.web("#FF6699"), "")
    override val type = "branch"
    override val tooltip = "Redirects bullets"
}

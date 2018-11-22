package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.logic.Direction
import com.durhack.sharpshot.gui.shapes.Triangle
import com.durhack.sharpshot.nodes.INode
import javafx.scene.paint.Color
import java.math.BigInteger

class BranchNode : INode() {
    override fun reset() {}
    override fun run(bullet: Bullet): Map<Direction, BigInteger?> = mapOf(
            Direction.UP to bullet.value)
    override fun graphic() = Triangle(rotation, Color.web("#FF6699"), "")
    override val type = "branch"
    override val tooltip = "Redirects bullets"
}

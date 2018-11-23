package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.gui.shapes.Diamond
import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.nodes.INode
import javafx.scene.paint.Color

class SplitterNode : INode() {
    /**
     * Shoot out 3 bullets in other directions
     */
    override fun run(bullet: Bullet) = bullet.direction.inverse.others.map { it to bullet.value }.toMap()

    override fun graphic() = Diamond(rotation, Color.web("#FF6699"), "Y")
    override fun reset() {}
    override val type = "splitter"
    override val tooltip = "A bullet in one side produces 3 bullets in the others"
}

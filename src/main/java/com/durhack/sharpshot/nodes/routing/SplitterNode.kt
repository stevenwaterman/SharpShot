package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.INode
import com.durhack.sharpshot.gui.Diamond
import javafx.scene.paint.Color

class SplitterNode : INode() {
    /**
     * Shoot out 3 bullets in other directions
     */
    override fun run(bullet: Bullet) = bullet.direction.inverse.others.map { it to bullet.value }.toMap()

    override fun toGraphic() = Diamond(rotation, Color.YELLOW, "Y")
    override fun reset() {}
    override fun toString() = "Splitter"
}

package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode
import com.durhack.sharpshot.gui.Diamond
import javafx.scene.Node
import javafx.scene.paint.Color

import java.math.BigInteger
import java.util.HashMap

class SplitterNode : INode {
    override var rotation = Direction.UP
        private set

    override fun rotateClockwise() {
        rotation = Direction.clockwiseOf(rotation)
    }

    /**
     * Shoot out 3 bullets in other directions
      */
    override fun run(bullet: Bullet) = Direction.others(Direction.inverseOf(bullet.direction)).map { it to bullet.value }.toMap()
    override fun toGraphic() = Diamond(rotation, Color.YELLOW, "Y")
    override fun reset() {}
    override fun toString() = "Splitter"
}

package com.durhack.sharpshot.nodes.io

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.gui.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger

class InNode(val index: Int?) : AbstractInputNode() {
    var input: BigInteger? = null
        private set

    override fun input(inputs: List<BigInteger>) =
            mapOf(Direction.UP to (if (index == null) null else inputs.getOrNull(index)))

    override fun run(bullet: Bullet) = mapOf(bullet.direction to bullet.value, Direction.UP to input)
    override fun toGraphic() = Triangle(rotation, Color.web("#FFFF00"), "IN${index ?: ""}")
    override fun reset() {}
    override fun toString() = "Input"
}

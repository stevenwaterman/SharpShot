package com.durhack.sharpshot.nodes.io

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.gui.Triangle
import com.durhack.sharpshot.gui.getNumberInput
import com.durhack.sharpshot.nodes.INode
import javafx.scene.paint.Color
import java.math.BigInteger

class InNode(val index: Int?) : AbstractInputNode() {
    var input: BigInteger? = null
        private set

    override fun input(inputs: List<BigInteger>) =
            mapOf(Direction.UP to (if (index == null) null else inputs.getOrNull(index)))

    override fun run(bullet: Bullet) = mapOf(bullet.direction to bullet.value, Direction.UP to input)
    override fun graphic() = Triangle(rotation, Color.web("#FFFF00"), "IN${index ?: ""}")
    override fun reset() {}
    override fun toString() = "Input"
    override val tooltip = "Provides Input at program start and every time a bullet passes through"
    override val factory = {
        val index = getNumberInput("Enter Input Index",
                                   "Blank to shoot empty bullet at start\nArguments are 0-indexed")?.toInt()
        when (index) {
            null -> null
            else -> InNode(index)
        }
    }
}

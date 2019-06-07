package com.durhack.sharpshot.core.nodes.other

import com.durhack.sharpshot.util.GRID_SIZE
import com.durhack.sharpshot.core.nodes.INode
import com.durhack.sharpshot.core.state.Direction
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import java.math.BigInteger

class HaltNode : INode() {
    override fun process(relativeDirection: Direction, value: BigInteger?) = mapOf<Direction, BigInteger?>()
    override fun graphic() = Circle(GRID_SIZE.toDouble() * 0.5, Color.RED)
    override val type = "halt"
    override fun reset() {}
    override val tooltip = "Terminates the program"
}

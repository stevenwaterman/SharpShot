package com.durhack.sharpshot.core.nodes.routing

import com.durhack.sharpshot.util.GRID_SIZE
import com.durhack.sharpshot.core.nodes.INode
import com.durhack.sharpshot.core.state.Direction
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import java.math.BigInteger

class VoidNode : INode() {
    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> = mapOf()
    override fun graphic() = Circle(GRID_SIZE.toDouble() * 0.5, Color.BLACK)
    override fun reset() {}
    override val type = "void"
    override val tooltip = "Consumes all incoming bullets"
}

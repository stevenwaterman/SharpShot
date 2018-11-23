package com.durhack.sharpshot.nodes.other

import com.durhack.sharpshot.GRID_SIZE
import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.logic.Direction
import com.durhack.sharpshot.nodes.INode
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import java.math.BigInteger

class HaltNode : INode() {
    override fun run(bullet: Bullet) = mapOf<Direction, BigInteger?>()
    override fun graphic() = Circle(GRID_SIZE.toDouble() * 0.5, Color.RED)
    override val type = "halt"
    override fun reset() {}
    override val tooltip = "Terminates the program"
}

package com.durhack.sharpshot.nodes.other

import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.logic.Direction
import com.durhack.sharpshot.GRID_SIZE
import com.durhack.sharpshot.nodes.INode
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import java.math.BigInteger
import java.util.*

class HaltNode : INode() {
    override fun run(bullet: Bullet): Map<Direction, BigInteger> {
        // halt checking is done within Container
        return HashMap()
    }

    override fun graphic(): Node {
        val rectangle = Rectangle(GRID_SIZE.toDouble(), GRID_SIZE.toDouble(), Color.GHOSTWHITE)
        val label = Label("HLT")
        return StackPane(rectangle, label)
    }

    override val type = "halt"

    override fun reset() {}
    override val tooltip = "Terminates the program"
}

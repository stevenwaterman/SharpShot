package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.GRID_SIZE
import com.durhack.sharpshot.nodes.INode
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class ACRotateNode : INode() {
    override fun run(bullet: Bullet) = mapOf(bullet.direction.antiClockwise to bullet.value)

    override fun graphic(): Node {
        val rectangle = Rectangle(GRID_SIZE.toDouble(), GRID_SIZE.toDouble(), Color.PALEVIOLETRED)
        val label = Label("ACW")
        return StackPane(rectangle, label)
    }

    override val type = "rotate anticlockwise"

    override fun reset() {}
    override val tooltip = "Rotates incoming bullets anticlockwise"
}

package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.nodes.INode
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class RotateNode : INode() {
    override fun graphic(): Node {
        val rectangle = Rectangle(32.0, 32.0, Color.PALEVIOLETRED)
        val label = Label("CW")
        return StackPane(rectangle, label)
    }

    override fun run(bullet: Bullet) = mapOf(bullet.direction.clockwise to bullet.value)
    override fun reset() {}
    override val type = "rotate clockwise"
    override val tooltip = "Rotates incoming bullets anticlockwise"
}

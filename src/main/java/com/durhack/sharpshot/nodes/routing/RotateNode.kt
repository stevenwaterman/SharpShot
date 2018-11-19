package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class RotateNode : INode {
    override var rotation = Direction.UP
        private set

    override fun rotateClockwise() {
        rotation = Direction.clockwiseOf(rotation)
    }

    override fun toGraphic(): Node {
        val rectangle = Rectangle(32.0, 32.0, Color.PALEVIOLETRED)
        val label = Label("CW")
        return StackPane(rectangle, label)
    }

    override fun run(bullet: Bullet) = mapOf(bullet.direction.clockwise() to bullet.value)
    override fun reset() {}
    override fun toString() = "Rotate Clockwise"
}

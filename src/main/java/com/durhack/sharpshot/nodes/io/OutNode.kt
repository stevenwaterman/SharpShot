package com.durhack.sharpshot.nodes.io

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.nodes.INode
import com.durhack.sharpshot.gui.App
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import java.math.BigInteger

class OutNode : INode() {
    override fun run(bullet: Bullet): Map<Direction, BigInteger> {
        val value = bullet.value
        if (value != null) {
            App.print(value.toString())
        }
        return mapOf()
    }

    override fun graphic(): Node {
        val rectangle = Rectangle(32.0, 32.0, Color.SANDYBROWN)
        val label = Label("INT")
        return StackPane(rectangle, label)
    }

    override fun toString() = "Print Integer"
    override fun reset() {}
    override val tooltip = "Consumes and prints bullets"
}

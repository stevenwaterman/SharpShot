package com.durhack.sharpshot.nodes.io

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode
import com.durhack.sharpshot.gui.App
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import java.math.BigInteger

class AsciiNode : INode() {
    override fun run(bullet: Bullet): Map<Direction, BigInteger> {
        val value = bullet.value
        if (value != null) {
            App.print(value.toChar())
        }
        return mapOf()
    }

    override fun toGraphic(): Node {
        val rectangle = Rectangle(32.0, 32.0, Color.FIREBRICK)
        val label = Label("CHR")
        return StackPane(rectangle, label)
    }

    override fun reset() {}
    override fun toString() = "Print Character"
}

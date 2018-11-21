package com.durhack.sharpshot.nodes.io

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.GRID_SIZE
import com.durhack.sharpshot.gui.OutputPane
import com.durhack.sharpshot.nodes.INode
import com.durhack.sharpshot.util.asChar
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*
import java.math.BigInteger

class AsciiNode : INode() {
    private val outputPane: OutputPane = find(OutputPane::class)

    override fun run(bullet: Bullet): Map<Direction, BigInteger> {
        val value = bullet.value
        val char = when (value) {
            null -> ' '
            else -> value.asChar()
        }
        outputPane.print(char)

        return mapOf()
    }

    override fun graphic(): Node {
        val rectangle = Rectangle(GRID_SIZE.toDouble(), GRID_SIZE.toDouble(), Color.FIREBRICK)
        val label = Label("CHR")
        return StackPane(rectangle, label)
    }

    override val type = "char print"

    override fun reset() {}
    override val tooltip = "Consumes and prints bullets based on their ASCII code equivalent. Nulls print as spaces"
}

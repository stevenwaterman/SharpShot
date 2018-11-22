package com.durhack.sharpshot.nodes.io

import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.logic.Direction
import com.durhack.sharpshot.GRID_SIZE
import com.durhack.sharpshot.gui.OutputPane
import com.durhack.sharpshot.nodes.INode
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*
import java.math.BigInteger

class OutNode : INode() {
    private val outputPane: OutputPane = find(OutputPane::class)

    override fun run(bullet: Bullet): Map<Direction, BigInteger> {
        val value = bullet.value ?: return mapOf()
        outputPane.print(value.toString())
        return mapOf()
    }

    override fun graphic(): Node {
        val rectangle = Rectangle(GRID_SIZE.toDouble(), GRID_SIZE.toDouble(), Color.SANDYBROWN)
        val label = Label("INT")
        return StackPane(rectangle, label)
    }

    override val type = "int print"

    override fun reset() {}
    override val tooltip = "Consumes and prints bullets"
}

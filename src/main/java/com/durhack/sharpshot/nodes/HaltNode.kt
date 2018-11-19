package com.durhack.sharpshot.nodes

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
import java.util.HashMap

class HaltNode : INode {
    override var rotation = Direction.UP
        private set

    override fun rotateClockwise() {
        rotation = Direction.clockwiseOf(rotation)
    }

    override fun toString(): String {
        return "Halt"
    }

    override fun run(bullet: Bullet): Map<Direction, BigInteger> {
        // halt checking is done within Container
        return HashMap()
    }

    override fun toGraphic(): Node {
        val rectangle = Rectangle(32.0, 32.0, Color.GHOSTWHITE)
        val label = Label("HLT")
        return StackPane(rectangle, label)
    }

    override fun reset() {}
}

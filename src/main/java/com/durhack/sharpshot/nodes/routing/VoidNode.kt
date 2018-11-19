package com.durhack.sharpshot.nodes.routing

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

import java.math.BigInteger
import java.util.HashMap

class VoidNode : INode {

    override var rotation = Direction.UP
        private set

    override fun toString(): String {
        return "Void"
    }

    override fun rotateClockwise() {
        rotation = Direction.clockwiseOf(rotation)
    }

    override fun run(bullet: Bullet): Map<Direction, BigInteger> {
        return HashMap()
    }

    override fun toGraphic(): Node {
        return Circle(16.0, Color.BLACK)//Rectangle(32.0, 32.0, Color.BLACK);
    }

    override fun reset() {}
}

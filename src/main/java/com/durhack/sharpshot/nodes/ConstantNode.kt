package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode
import com.durhack.sharpshot.gui.Triangle
import javafx.scene.Node
import javafx.scene.paint.Color
import java.math.BigInteger

class ConstantNode(var value: BigInteger?) : INode {
    override var rotation = Direction.UP
        private set

    override fun rotateClockwise() {
        rotation = Direction.clockwiseOf(rotation)
    }

    override fun run(bullet: Bullet): Map<Direction, BigInteger?> = mutableMapOf(bullet.direction to bullet.value,
                                                                                 Direction.UP to value // if both same direction, only constant comes out
                                                                                )

    override fun toGraphic(): Node {
        return Triangle(rotation, Color.LIMEGREEN, value!!.toString())
    }

    override fun reset() {}

    override fun toString(): String {
        return "Constant"
    }
}

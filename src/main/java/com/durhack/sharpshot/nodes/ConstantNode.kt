package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode
import com.durhack.sharpshot.gui.Triangle
import javafx.scene.Node
import javafx.scene.paint.Color
import java.math.BigInteger

class ConstantNode(var value: BigInteger?) : INode() {
    /**
     * If both the bullet and the output are in the same direction, the second element in the map overrides the first
     * so the input bullet is destroyed
     */
    override fun run(bullet: Bullet): Map<Direction, BigInteger?> = mutableMapOf(bullet.direction to bullet.value,
                                                                                 Direction.UP to value
                                                                                )

    override fun toGraphic(): Node {
        return Triangle(rotation, Color.LIMEGREEN, value!!.toString())
    }

    override fun reset() {}

    override fun toString(): String {
        return "Constant"
    }
}

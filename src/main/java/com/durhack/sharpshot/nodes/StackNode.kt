package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode
import com.durhack.sharpshot.gui.Triangle
import javafx.scene.Node
import javafx.scene.paint.Color
import java.math.BigInteger
import java.util.*

class StackNode : INode {
    override var rotation = Direction.UP
        private set
    private val stack = Stack<BigInteger>()

    override fun rotateClockwise() {
        rotation = rotation.clockwise()
    }

    override fun run(bullet: Bullet): Map<Direction, BigInteger> {
        val bullets = HashMap<Direction, BigInteger>()
        if (bullet.direction == Direction.UP) {
            if (!stack.isEmpty()) {
                bullets[Direction.UP] = stack.pop()
            }
        }
        else {
            stack.add(bullet.value)
        }
        return bullets
    }

    override fun toGraphic(): Node {
        return Triangle(rotation, Color.web("#FFFF00"), "S" + stack.size)
    }

    override fun reset() {
        stack.clear()
    }

    override fun toString(): String {
        return "Stack"
    }
}

package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.gui.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger
import java.util.*

class StackNode : INode() {
    private val stack = Stack<BigInteger?>()

    override fun run(bullet: Bullet): Map<Direction, BigInteger?> {
        val bullets = HashMap<Direction, BigInteger?>()
        if (bullet.direction == Direction.UP) {
            if (stack.isNotEmpty()) {
                bullets[Direction.UP] = stack.pop()
            }
        }
        else {
            stack.add(bullet.value)
        }
        return bullets
    }

    override fun reset() {
        stack.clear()
    }

    override val type = "stack"

    override fun graphic() = Triangle(rotation, Color.web("#FFFF00"), "S" + stack.size)
    override val tooltip = "Inputs in the back pop from the stack, inputs to other sides get added to the stack"
}

package com.durhack.sharpshot.core.nodes.other

import com.durhack.sharpshot.gui.shapes.Triangle
import com.durhack.sharpshot.core.nodes.INode
import com.durhack.sharpshot.core.state.Direction
import javafx.scene.paint.Color
import java.math.BigInteger
import java.util.*

class StackNode : INode() {
    private val stack = Stack<BigInteger?>()

    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> {
        val bullets = HashMap<Direction, BigInteger?>()
        if (relativeDirection == Direction.UP) {
            if (stack.isNotEmpty()) {
                bullets[Direction.UP] = stack.pop()
            }
        }
        else {
            stack.add(value)
        }
        return bullets
    }

    override fun reset() {
        stack.clear()
    }

    override val type = "stack"

    override fun graphic() = Triangle(direction, Color.web("#FFFF00"), "S" + stack.size)
    override val tooltip = "Inputs in the back pop from the stack, inputs to other sides get added to the stack"
}

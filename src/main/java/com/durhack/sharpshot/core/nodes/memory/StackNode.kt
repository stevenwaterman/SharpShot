package com.durhack.sharpshot.core.nodes.memory

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger
import java.util.*

class StackNode(direction: Direction) : AbstractNode(direction) {
    private val stack = Stack<BigInteger?>()
    val stackSize: Int get() = stack.size

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

    override fun copyWithDirection(direction: Direction) = StackNode(direction)
}

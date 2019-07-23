package com.durhack.sharpshot.core.nodes.memory

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger
import java.util.*

class MemoryNode(direction: Direction) : AbstractNode(direction) {
    var storedValue: BigInteger? = null

    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> {
        val bullets = HashMap<Direction, BigInteger?>()
        if (relativeDirection == Direction.UP) {
            bullets[Direction.UP] = storedValue
        }
        else {
            storedValue = value
        }
        return bullets
    }

    override fun reset() {
        storedValue = null
    }

    override val type = "memory"
}

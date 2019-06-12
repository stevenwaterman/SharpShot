package com.durhack.sharpshot.core.nodes.math

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

abstract class AbstractMathNode(direction: Direction) : AbstractNode(direction) {
    private var storedVal: BigInteger? = null

    protected abstract fun operation(val1: BigInteger, val2: BigInteger): BigInteger?

    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> {
        value ?: return mapOf()

        when(val immutableVal = storedVal){
            null -> {
                storedVal = value
                return mapOf()
            }
            else -> {
                val output = operation(immutableVal, value)
                storedVal = null
                return mapOf(Direction.UP to output)
            }
        }
    }

    override fun reset() {
        storedVal = null
    }
}

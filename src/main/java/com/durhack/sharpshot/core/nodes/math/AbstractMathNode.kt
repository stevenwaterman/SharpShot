package com.durhack.sharpshot.core.nodes.math

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger

abstract class AbstractMathNode : AbstractNode() {
    private var storedVal: BigInteger? = null

    protected abstract fun operation(val1: BigInteger, val2: BigInteger): BigInteger?

    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> {
        value ?: return mapOf()

        val immutableVal = storedVal
        when(immutableVal){
            null -> {
                storedVal = value
                return mapOf()
            }
            else -> {
                val output = operation(immutableVal, value)
                storedVal = null
                return mapOf(relativeDirection to output)
            }
        }
    }

    override fun reset() {
        storedVal = null
    }
}

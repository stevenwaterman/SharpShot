package com.durhack.sharpshot.core.nodes.other

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import java.math.BigInteger
import java.util.*

class RandomNode : AbstractNode() {
    private fun rand(max: BigInteger): BigInteger {
        var n = max
        var sign = 1
        if (n.compareTo(BigInteger.ZERO) == 0) {
            return BigInteger.ZERO
        }
        if (n < BigInteger.ZERO) {
            sign = -1
        }
        n = n.abs()
        val rand = Random()
        var result = BigInteger(n.bitLength(), rand)
        while (result >= n) {
            result = BigInteger(n.bitLength(), rand)
        }
        return result.multiply(BigInteger(sign.toString() + ""))
    }

    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> {
        val map = mutableMapOf(relativeDirection to value)
        if (value != null) {
            map[Direction.UP] = rand(value)
        }
        return map
    }

    override val type = "random"

    override fun reset() {}
    override val tooltip = "Provides a random output from 0 (inclusive) to initialise bullet value (exclusive)"
}

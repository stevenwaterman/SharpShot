package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import java.math.BigInteger
import java.util.*

class RandomNode : INode() {
    private fun rand(max: BigInteger): BigInteger {
        var n = max
        var sign = 1
        if (n.compareTo(BigInteger.ZERO) == 0) {
            return BigInteger.ZERO
        }
        if (n.compareTo(BigInteger.ZERO) < 0) {
            sign = -1
        }
        n = n.abs()
        val rand = Random()
        var result = BigInteger(n.bitLength(), rand)
        while (result.compareTo(n) >= 0) {
            result = BigInteger(n.bitLength(), rand)
        }
        return result.multiply(BigInteger(sign.toString() + ""))
    }

    override fun run(bullet: Bullet): Map<Direction, BigInteger> {
        val map = HashMap<Direction, BigInteger>()
        if (bullet.value != null) {
            map[rotation] = rand(bullet.value)
        }
        return map
    }

    override fun graphic() = Rectangle(32.0, 32.0, Color.GREEN)
    override fun reset() {}
    override fun toString() = "Random"
    override val tooltip = "Provides a random output from 0 (inclusive) to input bullet value (exclusive)"
}

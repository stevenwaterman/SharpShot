package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.GRID_SIZE
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
        if (n < BigInteger.ZERO) {
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

    override val type = "random"

    override fun graphic() = Rectangle(GRID_SIZE.toDouble(), GRID_SIZE.toDouble(), Color.GREEN)
    override fun reset() {}
    override val tooltip = "Provides a random output from 0 (inclusive) to input bullet value (exclusive)"
}

package com.durhack.sharpshot.nodes.other

import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.logic.Direction
import com.durhack.sharpshot.GRID_SIZE
import com.durhack.sharpshot.nodes.INode
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
        while (result >= n) {
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

package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import java.math.BigInteger
import java.util.*

class RandomNode : INode {
    override var rotation = Direction.UP
        private set

    override fun rotateClockwise() {
        rotation = Direction.clockwiseOf(rotation)
    }

    override fun toString(): String {
        return "Random"
    }

    private fun rand(n: BigInteger): BigInteger {
        var n = n
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

    override fun toGraphic(): Node {
        return Rectangle(32.0, 32.0, Color.GREEN)
    }

    override fun reset() {
        //nothing to do
    }
}

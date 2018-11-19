package com.durhack.sharpshot.nodes.math

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.nodes.INode
import java.math.BigInteger

abstract class AbstractMathNode : INode() {
    private var mostRecentBullet: Bullet? = null

    protected abstract fun operation(val1: BigInteger, val2: BigInteger): BigInteger?

    override fun run(bullet: Bullet): Map<Direction, BigInteger?> {
        val value = bullet.value
        val firstBullet = mostRecentBullet

        return when {
            value == null       -> mapOf()
            firstBullet == null -> {
                // First bullet
                mostRecentBullet = bullet
                mapOf()
            }
            else                -> {
                mostRecentBullet = null
                mapOf(Direction.UP to operation(value, bullet.value))
            }
        }
    }

    override fun reset() {
        mostRecentBullet = null
    }


}

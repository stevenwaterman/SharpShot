package com.durhack.sharpshot.nodes.io

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.gui.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger
import java.util.*

class ListNode : AbstractInputNode() {
    override var rotation = Direction.UP
        private set

    private var inputs: List<BigInteger>? = null
    private var nextOutputIndex = 0

    override fun rotateClockwise() {
        rotation = Direction.clockwiseOf(rotation)
    }

    override fun run(bullet: Bullet): Map<Direction, BigInteger?> {
        val bullets = mutableMapOf(bullet.direction to bullet.value)

        if (nextOutputIndex < inputs!!.size) {
            bullets[Direction.UP] = inputs!![nextOutputIndex]
            nextOutputIndex++
        }

        return bullets
    }

    override fun reset() {
        inputs = ArrayList()
        nextOutputIndex = 0
    }

    override fun input(inputs: List<BigInteger>): Map<Direction, BigInteger> {
        this.inputs = inputs
        return HashMap()
    }

    override fun toGraphic() = Triangle(rotation, Color.web("#FFFF00"), "LST")
    override fun toString() = "Argument List"
}

package com.durhack.sharpshot.nodes.input

import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.logic.Direction
import com.durhack.sharpshot.gui.shapes.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger

class ListNode : AbstractInputNode() {
    private val inputs: MutableList<BigInteger?> = mutableListOf()
    private var nextOutputIndex = 0

    override fun input(inputs: List<BigInteger?>): Map<Direction, BigInteger> {
        this.inputs.clear()
        this.inputs.addAll(inputs)
        return mapOf()
    }

    override fun run(bullet: Bullet): Map<Direction, BigInteger?> {
        val bullets = mutableMapOf(bullet.direction to bullet.value)

        if (nextOutputIndex < inputs.size) {
            bullets[Direction.UP] = inputs[nextOutputIndex]
            nextOutputIndex++
        }

        return bullets
    }

    override fun reset() {
        inputs.clear()
        nextOutputIndex = 0
    }

    override val type = "list"

    override fun graphic() = Triangle(rotation, Color.web("#FFFF00"), "LST")
    override val tooltip = "Every time a bullet comes in, outputs the next value in the list of inputs"
}

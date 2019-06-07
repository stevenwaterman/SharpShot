package com.durhack.sharpshot.core.nodes.input

import com.durhack.sharpshot.gui.shapes.Triangle
import com.durhack.sharpshot.core.state.Direction
import javafx.scene.paint.Color
import java.math.BigInteger

class ListNode : AbstractInputNode() {
    private val inputs: MutableList<BigInteger?> = mutableListOf()
    private var nextIndex = 0

    override fun initialise(inputs: List<BigInteger?>): Pair<Direction, BigInteger>? {
        this.inputs.clear()
        this.inputs.addAll(inputs)
        return null
    }

    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> {
        val bullets = mutableMapOf(relativeDirection to value)
        if (nextIndex < inputs.size) {
            bullets[Direction.UP] = inputs[nextIndex]
            nextIndex++
        }
        return bullets
    }

    override fun reset() {
        inputs.clear()
        nextIndex = 0
    }

    override val type = "list"

    override fun graphic() = Triangle(direction, Color.web("#FFFF00"), "LST")
    override val tooltip = "Every time a bullet comes in, outputs the next value in the list of inputs"
}

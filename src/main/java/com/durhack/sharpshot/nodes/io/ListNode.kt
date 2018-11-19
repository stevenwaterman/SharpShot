package com.durhack.sharpshot.nodes.io

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.gui.Triangle
import javafx.scene.paint.Color
import java.math.BigInteger

class ListNode : AbstractInputNode() {
    private var inputs: MutableList<BigInteger> = mutableListOf()
    private var nextOutputIndex = 0

    override fun input(inputs: List<BigInteger>): Map<Direction, BigInteger> {
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

    override fun toGraphic() = Triangle(rotation, Color.web("#FFFF00"), "LST")
    override fun toString() = "Argument List"
}

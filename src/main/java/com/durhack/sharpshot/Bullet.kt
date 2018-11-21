package com.durhack.sharpshot

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

import java.math.BigInteger

/**
 * Immutable
 */
class Bullet(val direction: Direction, val value: BigInteger?) {
    fun toGraphic(): Node {
        val stackPane = StackPane()

        val background = Rectangle(GRID_SIZE.toDouble() * 0.5, GRID_SIZE.toDouble() * 0.5, Color.WHEAT)
        stackPane.children.add(background)
        stackPane.alignment = Pos.CENTER

        stackPane.prefWidth = GRID_SIZE.toDouble()
        stackPane.prefHeight = GRID_SIZE.toDouble()

        if (value != null) {
            val label = Label(value.toString())
            stackPane.children.add(label)
        }

        return stackPane
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bullet

        if (direction != other.direction) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = direction.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        return result
    }
}

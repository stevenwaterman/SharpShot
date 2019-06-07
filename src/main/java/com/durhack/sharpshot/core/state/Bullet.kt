package com.durhack.sharpshot.core.state

import com.durhack.sharpshot.util.GRID_SIZE
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
data class Bullet(val coordinate: Coordinate, val direction: Direction, val value: BigInteger?) {
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

    fun increment() = Bullet(nextCoord(), direction, value)
    fun nextCoord() = coordinate + direction

    override fun toString() = "Bullet(coordinate = $coordinate, direction=$direction, value=$value)"
}

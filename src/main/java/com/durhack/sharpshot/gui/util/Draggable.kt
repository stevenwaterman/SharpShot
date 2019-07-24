package com.durhack.sharpshot.gui.util

import com.durhack.sharpshot.core.state.Direction
import javafx.beans.binding.IntegerExpression
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Point2D
import javafx.scene.Node
import javafx.scene.input.MouseButton
import tornadofx.*

fun Node.makeDraggable(amountPerClick: Int, onDragged: (Point2D, Point2D, Direction) -> Boolean) = makeDraggable(
        SimpleIntegerProperty(
        amountPerClick), onDragged)

fun Node.makeDraggable(amountPerClickProperty: IntegerExpression, onDragged: (Point2D, Point2D, Direction) -> Boolean) {

    var dragStart: Point2D? = null
    var consumedAmount: Point2D = Point2D.ZERO

    setOnMousePressed {
        if (it.button != MouseButton.PRIMARY) return@setOnMousePressed
        it.consume()
        dragStart = localToScreen(Point2D(it.x, it.y))
        consumedAmount = Point2D.ZERO
    }

    setOnMouseDragged {
        if (it.button != MouseButton.PRIMARY) return@setOnMouseDragged
        it.consume()
        val dragStartCapt = dragStart ?: return@setOnMouseDragged
        val amountPerClick = amountPerClickProperty.get().toDouble()
        val dragEnd = localToScreen(Point2D(it.x, it.y))
        val delta = dragEnd - dragStartCapt
        var newDelta = delta - consumedAmount

        while (newDelta.x > amountPerClick) {
            val clicked = onDragged(dragStartCapt, dragEnd, Direction.RIGHT)
            if (clicked) {
                val change = Point2D(amountPerClick, 0.0)
                newDelta -= change
                consumedAmount += change
            }
            else {
                break
            }
        }
        while (newDelta.x < -amountPerClick) {
            val clicked = onDragged(dragStartCapt, dragEnd, Direction.LEFT)
            if (clicked) {
                val change = Point2D(amountPerClick, 0.0)
                newDelta += change
                consumedAmount -= change
            }
            else {
                break
            }
        }
        while (newDelta.y > amountPerClick) {
            val clicked = onDragged(dragStartCapt, dragEnd, Direction.DOWN)
            if (clicked) {
                val change = Point2D(0.0, amountPerClick)
                newDelta -= change
                consumedAmount += change
            }
            else {
                break
            }
        }
        while (newDelta.y < -amountPerClick) {
            val clicked = onDragged(dragStartCapt, dragEnd, Direction.UP)
            if (clicked) {
                val change = Point2D(0.0, amountPerClick)
                newDelta += change
                consumedAmount -= change
            }
            else {
                break
            }
        }
    }
}
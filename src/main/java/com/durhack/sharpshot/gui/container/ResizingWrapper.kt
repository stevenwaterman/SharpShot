package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.core.control.canDecreaseSize
import com.durhack.sharpshot.core.control.decreaseSize
import com.durhack.sharpshot.core.control.increaseSize
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.menus.ContainerInputLayer
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.util.container
import javafx.beans.binding.IntegerExpression
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Point2D
import javafx.scene.Node
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import tornadofx.*

fun Node.makeDraggable(amountPerClick: Int, onDragged: (Direction) -> Boolean) = makeDraggable(SimpleIntegerProperty(
        amountPerClick), onDragged)

fun Node.makeDraggable(amountPerClickProperty: IntegerExpression, onDragged: (Direction) -> Boolean) {

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
            val clicked = onDragged(Direction.RIGHT)
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
            val clicked = onDragged(Direction.LEFT)
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
            val clicked = onDragged(Direction.DOWN)
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
            val clicked = onDragged(Direction.UP)
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

private class DraggableCorner(val vertical: Direction, val horizontal: Direction) : Fragment() {
    private val containerView: ContainerView by inject()

    override val root = hbox {
        id = "Draggable Corner"

        canvas(25.0, 25.0) {
            Draw.rightAngleTriangle(graphicsContext2D, vertical, 0.0, 0.0, 20, Color.BLACK)
            makeDraggable(ContainerView.innerScaleProp.divide(2)) { direction ->
                if (direction == vertical || direction == horizontal) {
                    container.increaseSize(direction)
                    containerView.render()
                    true
                }
                else {
                    if (container.canDecreaseSize(direction)) {
                        container.decreaseSize(direction)
                        containerView.render()
                        true
                    }
                    else {
                        false
                    }
                }
            }
        }

        addEventHandler(MouseEvent.MOUSE_DRAGGED) {
            if (it.button != MouseButton.SECONDARY) {
                it.consume()
            }
        }
    }
}

class ResizingWrapper : View() {
    private val paddingAmnt = 50
    private val topArrow = DraggableCorner(Direction.UP, Direction.LEFT)
    private val bottomArrow = DraggableCorner(Direction.DOWN, Direction.RIGHT)
    private val containerView: ContainerView by inject()
    private val containerInputLayer: ContainerInputLayer by inject()

    override val root = gridpane {
        id = "Resizing Wrapper"

        enableWhen(container.runningProp.booleanBinding { it?.not() ?: true })
        paddingAll = paddingAmnt
        add(topArrow.root, 0, 0)
        add(bottomArrow.root, 2, 2)
        add(containerView.root, 1, 1)
        add(containerInputLayer.root, 1, 1)

        maxWidthProperty().bind(topArrow.root.maxWidthProperty() + containerView.root.maxWidthProperty() + bottomArrow.root.maxWidthProperty() + paddingAmnt)
        maxHeightProperty().bind(topArrow.root.maxHeightProperty() + containerView.root.maxHeightProperty() + bottomArrow.root.maxHeightProperty() + paddingAmnt)
    }
}
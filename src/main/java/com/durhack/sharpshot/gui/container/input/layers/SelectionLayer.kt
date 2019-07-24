package com.durhack.sharpshot.gui.container.input.layers

import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerView
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*

class SelectionLayer : View() {
    private val rectangle = Rectangle(100.0, 100.0, Color.RED).apply {
        isVisible = true
        addEventHandler(MouseEvent.ANY) {
            it.consume()
        }
    }

    private val containerView: ContainerView by inject()
    private var selected: SelectedRange? = null

    fun clear() {
        selected = null
        render()
    }

    fun select(xRange: IntRange, yRange: IntRange) {
        selected = SelectedRange(xRange, yRange)
        render()
    }

    fun render() {
        val capt = selected
        if (capt == null) {
            rectangle.hide()
        }
        else {
            val xRange = capt.xRange
            val xCoordMin = xRange.first
            val xCoordMax = xRange.endInclusive

            val yRange = capt.yRange
            val yCoordMin = yRange.first
            val yCoordMax = yRange.endInclusive

            val minCoord = Coordinate(xCoordMin, yCoordMin)
            val maxCoord = Coordinate(xCoordMax, yCoordMax)

            val minPoint = containerView.getPoint(minCoord)
            val maxPoint = containerView.getPoint(maxCoord)

            val xMin = minPoint.x
            val xMax = maxPoint.x

            val yMin = minPoint.y
            val yMax = maxPoint.y

            rectangle.x = xMin
            rectangle.width = (xMax - xMin)

            rectangle.y = yMin
            rectangle.height = (yMax - yMin)

            rectangle.show()
        }
    }

    override val root = pane {
        add(rectangle)
    }

    private data class SelectedRange(val xRange: IntRange, val yRange: IntRange)
}
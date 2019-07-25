package com.durhack.sharpshot.gui.container.input.layers.popovers

import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.util.CoordinateRange2D
import com.durhack.sharpshot.gui.util.FractionalCoordinate
import javafx.geometry.Insets
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.math.*

class SelectionMenu : View() {
    private val containerView: ContainerView by inject()
    private var selected: CoordinateRange2D? = null

    val isSelected: Boolean get() = selected != null

    override val root = stackpane {
        id = "Selection Menu"

        background = Background(BackgroundFill(Color(1.0, 0.0, 0.0, 0.2), CornerRadii.EMPTY, Insets.EMPTY))
        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(3.0)))

        addEventHandler(MouseEvent.ANY) {
            println("hit")
            it.consume()
        }
    }

    fun hide() {
        selected = null
        render()
    }

    fun show(start: FractionalCoordinate, end: FractionalCoordinate) {
        val startX = start.x
        val endX = end.x
        val xRange = intRange(startX, endX)

        val endY = end.y
        val startY = start.y
        val yRange = intRange(startY, endY)

        selected = CoordinateRange2D(xRange, yRange)
        render()
    }

    private fun intRange(a: Double, b: Double): IntRange {
        val min = min(a, b)
        val max = max(a, b)

        val floor = floor(min).roundToInt()
        val ceil = ceil(max).roundToInt()

        return floor..ceil
    }

    fun render() {
        val capt = selected
        if (capt == null) {
            root.isVisible = false
        }
        else {
            val xRange = capt.xRange
            val xCoordMin = xRange.first
            val xCoordMax = xRange.endInclusive

            val yRange = capt.yRange
            val yCoordMin = yRange.first
            val yCoordMax = yRange.endInclusive

            val minCoord = FractionalCoordinate(xCoordMin, yCoordMin)
            val maxCoord = FractionalCoordinate(xCoordMax, yCoordMax)

            val minPoint = containerView.getPoint(minCoord)
            val maxPoint = containerView.getPoint(maxCoord)

            val xMin = minPoint.x
            val xMax = maxPoint.x

            val yMin = minPoint.y
            val yMax = maxPoint.y

            root.layoutX = xMin
            root.layoutY = yMin

            root.prefWidth = (xMax - xMin)
            root.prefHeight = (yMax - yMin)

            root.isVisible = true
        }
    }
}
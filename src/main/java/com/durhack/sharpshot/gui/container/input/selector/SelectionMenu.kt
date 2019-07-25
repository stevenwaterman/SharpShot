package com.durhack.sharpshot.gui.container.input.selector

import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerView
import javafx.geometry.Insets
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*

class SelectionMenu : View() {
    private val containerView: ContainerView by inject()

    override val root = stackpane {
        background = Background(BackgroundFill(Color(1.0, 0.0, 0.0, 0.2), CornerRadii.EMPTY, Insets.EMPTY))
        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(3.0)))

        addEventHandler(MouseEvent.ANY) {
            it.consume()
        }
    }

    fun show(selected: CoordinateRange2D) {
        val xRange = selected.xRange
        val xCoordMin = xRange.first
        val xCoordMax = xRange.endInclusive

        val yRange = selected.yRange
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

        root.layoutX = xMin
        root.layoutY = yMin

        root.prefWidth = (xMax - xMin)
        root.prefHeight = (yMax - yMin)

        root.isVisible = true
    }

    fun hide() {
        root.isVisible = false
    }
}
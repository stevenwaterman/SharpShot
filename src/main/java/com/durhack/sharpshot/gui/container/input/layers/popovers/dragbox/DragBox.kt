package com.durhack.sharpshot.gui.container.input.layers.popovers.dragbox

import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.util.FractionalCoordinate
import javafx.geometry.Point2D
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.math.abs
import kotlin.math.min

class DragBox : View() {
    private val containerView: ContainerView by inject()

    override val root = pane {
        isVisible = false
        background = Background.EMPTY
        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths(1.0)))
    }

    fun hide() {
        root.isVisible = false
    }

    fun show(startCoord: FractionalCoordinate, endPoint: Point2D) {
        val startPoint = containerView.getPoint(startCoord)

        val x1 = startPoint.x
        val x2 = endPoint.x
        val y1 = startPoint.y
        val y2 = endPoint.y

        val xMin = min(x1, x2)
        val xRange = abs(x1 - x2)
        val yMin = min(y1, y2)
        val yRange = abs(y1 - y2)

        root.layoutX = xMin
        root.layoutY = yMin
        root.prefWidth = xRange
        root.prefHeight = yRange
        root.isVisible = true
    }
}
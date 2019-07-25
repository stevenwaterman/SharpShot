package com.durhack.sharpshot.gui.container.input.layers.popovers

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
        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths(2.0)))
    }

    private var position: DragBoxPosition? = null

    fun show(start: FractionalCoordinate, end: Point2D) {
        position = DragBoxPosition(start, end)
        render()
    }

    fun hide() {
        position = null
        render()
    }

    fun render() {
        val capt = position
        if (capt == null) {
            root.isVisible = false
        }
        else {
            val (startCoord, endPoint) = capt
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

    private data class DragBoxPosition(val start: FractionalCoordinate, val end: Point2D)
}
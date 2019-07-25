package com.durhack.sharpshot.gui.container.input.layers.popovers.dragbox

import javafx.geometry.Point2D
import tornadofx.*
import kotlin.math.abs
import kotlin.math.min

class DragBoxPositioner : View() {
    private val box: DragBox by inject()

    override val root = pane {
        id = "Drag Rectangle Positioner"
        add(box)
    }

    fun show(start: Point2D, end: Point2D) {
        val x1 = start.x
        val x2 = end.x
        val y1 = start.y
        val y2 = end.y

        val xMin = min(x1, x2)
        val xRange = abs(x1 - x2)
        val yMin = min(y1, y2)
        val yRange = abs(y1 - y2)

        val root = box.root
        root.layoutX = xMin
        root.layoutY = yMin
        root.prefWidth = xRange
        root.prefHeight = yRange
        root.show()
    }

    fun hide() {
        box.root.hide()
    }
}
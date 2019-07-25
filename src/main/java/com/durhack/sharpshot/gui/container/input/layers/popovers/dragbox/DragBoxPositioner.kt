package com.durhack.sharpshot.gui.container.input.layers.popovers.dragbox

import com.durhack.sharpshot.gui.util.FractionalCoordinate
import javafx.geometry.Point2D
import tornadofx.*

class DragBoxPositioner : View() {
    private val box: DragBox by inject()

    private var position: DragBoxPosition? = null

    override val root = pane {
        id = "Drag Rectangle Positioner"
        add(box)
    }

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
            box.hide()
        }
        else {
            box.show(capt.start, capt.end)
        }
    }

    private data class DragBoxPosition(val start: FractionalCoordinate, val end: Point2D)
}
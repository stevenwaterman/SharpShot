package com.durhack.sharpshot.gui.container.input.layers.popovers.selector

import com.durhack.sharpshot.gui.util.CoordinateRange2D
import com.durhack.sharpshot.gui.util.FractionalCoordinate
import tornadofx.*
import kotlin.math.*

class SelectionPositioner : View() {
    val isSelected: Boolean get() = selected != null

    private val selectionMenu: SelectionMenu by inject()

    private var selected: CoordinateRange2D? = null

    fun clear() {
        selected = null
        render()
    }

    fun select(start: FractionalCoordinate, end: FractionalCoordinate) {
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
            selectionMenu.hide()
        }
        else {
            selectionMenu.show(capt)
        }
    }

    override val root = pane {
        id = "Selection Positioner"
        add(selectionMenu)
    }
}
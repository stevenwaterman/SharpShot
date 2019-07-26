package com.durhack.sharpshot.gui.input.layers

import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.input.layers.popovers.DragBox
import com.durhack.sharpshot.gui.util.CoordinateRange2D
import com.durhack.sharpshot.gui.util.FractionalCoordinate
import com.durhack.sharpshot.gui.util.addClickHandler
import com.durhack.sharpshot.gui.util.point
import com.durhack.sharpshot.util.globalContainer
import com.durhack.sharpshot.util.globalSelection
import javafx.scene.input.MouseButton
import javafx.scene.layout.Priority
import tornadofx.*
import kotlin.math.*

class BoardSelector : View() {
    private val containerView: ContainerView by inject()
    private val dragBox: DragBox by inject()

    private var dragStart: FractionalCoordinate? = null
    private var dragEnd: FractionalCoordinate? = null
    var dragging = false
        private set

    override val root = stackpane {
        id = "Board Selector"
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS

        addClickHandler {
            if (it.button == MouseButton.SECONDARY) {
                hideSelection()
            }
        }

        setOnMousePressed {
            if (it.button == MouseButton.PRIMARY) {
                val point = it.point
                val coord = containerView.getCoord(point)
                hideSelection()
                dragStart = coord
            }
        }

        setOnMouseReleased {
            if (it.button == MouseButton.PRIMARY) {
                val start = dragStart
                val end = dragEnd

                if (start != null && end != null) {
                    showSelection(start, end)
                    dragBox.hide()
                }

                dragStart = null
                dragEnd = null
                dragging = false
            }
        }

        setOnMouseDragged {
            val start = dragStart ?: return@setOnMouseDragged

            /*
            The isStillSincePress allows for a little bit of wiggle room when clicking before the drag kicks in
            Called the "system hysteresis area"
            Without this, small drags would result in both an area selection and a node creation menu popover
             */
            if (it.button == MouseButton.PRIMARY && !it.isStillSincePress) {
                dragging = true
                val point = it.point
                dragEnd = containerView.getCoord(point)
                dragBox.show(start, point)
            }
        }
    }

    private fun hideSelection() {
        globalSelection = null
    }

    private fun showSelection(start: FractionalCoordinate, end: FractionalCoordinate) {
        val startX = start.x
        val endX = end.x
        val xRange = intRange(startX, endX)
        val validX = 0..globalContainer.width
        val clampX = xRange.intersect(validX)

        val startY = start.y
        val endY = end.y
        val yRange = intRange(startY, endY)
        val validY = 0..globalContainer.height
        val clampY = yRange.intersect(validY)

        val range = CoordinateRange2D(clampX, clampY)
        //Don't allow selecting an empty range because there's no point
        if (globalContainer.nodes.keys.any { it in range }) {
            globalSelection = range
        }
    }

    private fun intRange(a: Double, b: Double): IntRange {
        val min = min(a, b)
        val max = max(a, b)

        val floor = floor(min).roundToInt()
        val ceil = ceil(max).roundToInt()

        return floor..ceil
    }

    private fun IntRange.intersect(oth: IntRange): IntRange {
        val min1 = first
        val min2 = oth.first
        val min = max(min1, min2)

        val max1 = endInclusive
        val max2 = oth.endInclusive
        val max = min(max1, max2)

        return min..max
    }
}
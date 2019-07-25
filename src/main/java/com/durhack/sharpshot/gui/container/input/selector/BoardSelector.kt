package com.durhack.sharpshot.gui.container.input.selector

import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.input.layers.CreateNodeClickLayer
import com.durhack.sharpshot.gui.util.addClickHandler
import com.durhack.sharpshot.gui.util.point
import javafx.scene.input.MouseButton
import javafx.scene.layout.Priority
import tornadofx.*
import kotlin.math.abs
import kotlin.math.min

class BoardSelector : View() {
    val nodeCreator: CreateNodeClickLayer by inject()
    val containerView: ContainerView by inject()
    val selectionPositioner: SelectionPositioner by inject()

    private var dragStart: Coordinate? = null
    private var dragEnd: Coordinate? = null
    var dragging = false
        private set

    override val root = stackpane {
        id = "Board Selector"
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS

        add(nodeCreator)

        addClickHandler {
            if (selectionPositioner.isSelected && it.button == MouseButton.SECONDARY) {
                selectionPositioner.clear()
            }

        }

        setOnMousePressed {
            if (it.button == MouseButton.PRIMARY) {
                val coord = containerView.getCoord(it.point)
                if (coord != null && coord.exists) {
                    selectionPositioner.clear()
                    dragStart = coord
                }
            }
        }

        setOnMouseReleased {
            if (it.button == MouseButton.PRIMARY) {
                val start = dragStart;
                val end = dragEnd

                if (start != null && end != null) {
                    val startX = start.x
                    val startY = start.y

                    val endX = end.x
                    val endY = end.y

                    val minX = min(startX, endX)
                    val xRange = abs(startX - endX)
                    val maxX = minX + xRange + 1

                    val minY = min(startY, endY)
                    val yRange = abs(startY - endY)
                    val maxY = minY + yRange + 1

                    selectionPositioner.select(minX..maxX, minY..maxY)
                }

                dragStart = null
                dragEnd = null
                dragging = false
            }
        }

        setOnMouseDragged {
            dragStart ?: return@setOnMouseDragged

            if (it.button == MouseButton.PRIMARY) {
                val coord = containerView.getCoord(it.point)
                if (coord != null && coord.exists) {
                    dragging = true

                    val end: Coordinate = coord
                    dragEnd = end


                }
            }
        }
    }
}



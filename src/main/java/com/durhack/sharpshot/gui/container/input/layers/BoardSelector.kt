package com.durhack.sharpshot.gui.container.input.layers

import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.input.layers.popovers.dragbox.DragBoxPositioner
import com.durhack.sharpshot.gui.container.input.layers.popovers.selector.SelectionPositioner
import com.durhack.sharpshot.gui.util.FractionalCoordinate
import com.durhack.sharpshot.gui.util.addClickHandler
import com.durhack.sharpshot.gui.util.point
import javafx.scene.input.MouseButton
import javafx.scene.layout.Priority
import tornadofx.*

class BoardSelector : View() {
    private val containerView: ContainerView by inject()
    private val selectionPositioner: SelectionPositioner by inject()
    private val dragBoxPositioner: DragBoxPositioner by inject()

    private var dragStart: FractionalCoordinate? = null
    private var dragEnd: FractionalCoordinate? = null
    var dragging = false
        private set

    override val root = stackpane {
        id = "Board Selector"
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS

        addClickHandler {
            if (selectionPositioner.isSelected && it.button == MouseButton.SECONDARY) {
                selectionPositioner.clear()
            }
        }

        setOnMousePressed {
            if (it.button == MouseButton.PRIMARY) {
                val point = it.point
                val coord = containerView.getCoord(point)
                selectionPositioner.clear()
                dragStart = coord
            }
        }

        setOnMouseReleased {
            if (it.button == MouseButton.PRIMARY) {
                val start = dragStart
                val end = dragEnd

                if (start != null && end != null) {
                    selectionPositioner.select(start, end)
                    dragBoxPositioner.hide()
                }

                dragStart = null
                dragEnd = null
                dragging = false
            }
        }

        setOnMouseDragged {
            val start = dragStart ?: return@setOnMouseDragged

            if (it.button == MouseButton.PRIMARY) {
                dragging = true
                val point = it.point
                dragEnd = containerView.getCoord(point)
                dragBoxPositioner.show(start, point)
            }
        }
    }
}
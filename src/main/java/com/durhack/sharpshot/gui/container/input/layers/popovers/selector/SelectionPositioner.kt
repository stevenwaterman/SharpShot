package com.durhack.sharpshot.gui.container.input.layers.popovers.selector

import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.util.CoordinateRange2D
import tornadofx.*

class SelectionPositioner : View() {
    val isSelected: Boolean get() = selected != null

    private val selectionMenu: SelectionMenu by inject()

    private val containerView: ContainerView by inject()
    private var selected: CoordinateRange2D? = null

    fun clear() {
        selected = null
        render()
    }

    fun select(xRange: IntRange, yRange: IntRange) {
        selected = CoordinateRange2D(xRange, yRange)
        render()
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
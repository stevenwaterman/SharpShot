package com.durhack.sharpshot.gui.container.input.layers

import com.durhack.sharpshot.gui.container.input.layers.popovers.createnode.CreateNodePositioner
import com.durhack.sharpshot.gui.container.input.layers.popovers.dragbox.DragBoxPositioner
import com.durhack.sharpshot.gui.container.input.layers.popovers.selector.SelectionPositioner
import tornadofx.*

/**
 * Each popover must have a positioner layer which does not capture any user input.
 * The positioner positions a smaller GUI over the screen, which is allowed to take user input.
 *
 */
class ViewPopover : View() {
    val selectionPositioner: SelectionPositioner by inject()
    val createNodePositioner: CreateNodePositioner by inject()
    val dragBoxPositioner: DragBoxPositioner by inject()

    private val layers = listOf(
            selectionPositioner,
            createNodePositioner,
            dragBoxPositioner
                               )

    override val root = stackpane {
        id = "View Popover"

        layers.forEach {
            add(it)
        }
    }
}
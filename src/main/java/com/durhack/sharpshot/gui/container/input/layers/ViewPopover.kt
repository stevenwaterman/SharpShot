package com.durhack.sharpshot.gui.container.input.layers

import com.durhack.sharpshot.gui.container.input.layers.popovers.CreateNodeMenu
import com.durhack.sharpshot.gui.container.input.layers.popovers.DragBox
import com.durhack.sharpshot.gui.container.input.layers.popovers.SelectionMenu
import tornadofx.*

/**
 * Each popover must have a positioner layer which does not capture any user input.
 * The positioner positions a smaller GUI over the screen, which is allowed to take user input.
 *
 */
class ViewPopover : View() {
    val createNodeMenu: CreateNodeMenu by inject()
    val dragBox: DragBox by inject()
    val selectionMenu: SelectionMenu by inject()

    override val root = pane {
        id = "View Popover"

        add(createNodeMenu)
        add(dragBox)
        add(selectionMenu)
    }
}
package com.durhack.sharpshot.gui.container.input.layers

import com.durhack.sharpshot.gui.container.input.createnode.CreateNodePositioner
import com.durhack.sharpshot.gui.container.input.selector.SelectionPositioner
import tornadofx.*

class ViewPopover : View() {
    val selectionPositioner: SelectionPositioner by inject()
    val createNodePositioner: CreateNodePositioner by inject()

    override val root = stackpane {
        id = "View Popover"

        add(selectionPositioner)
        add(createNodePositioner)
    }
}
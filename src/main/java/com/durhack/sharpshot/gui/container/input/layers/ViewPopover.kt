package com.durhack.sharpshot.gui.container.input.layers

import com.durhack.sharpshot.gui.container.input.createnode.CreateNodePositioner
import tornadofx.*

class ViewPopover : View() {
    val selectionLayer: SelectionLayer by inject()
    val createNodePositioner: CreateNodePositioner by inject()

    override val root = stackpane {
        add(selectionLayer)
        add(createNodePositioner)
    }
}
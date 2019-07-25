package com.durhack.sharpshot.gui.container.input.createnode

import tornadofx.*

class CreateNodePositioner : View() {
    val createNodeMenu: CreateNodeMenu by inject()

    override val root = pane {
        id = "Create Node Positioner"
        add(createNodeMenu)
    }
}
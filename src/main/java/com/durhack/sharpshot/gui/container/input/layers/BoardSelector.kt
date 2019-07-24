package com.durhack.sharpshot.gui.container.input.layers

import javafx.scene.layout.Priority
import tornadofx.*

class BoardSelector : View() {
    val nodeCreator: CreateNodeClickLayer by inject()

    override val root = stackpane {
        id = "Board Selector"
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS

        add(nodeCreator)
    }
}
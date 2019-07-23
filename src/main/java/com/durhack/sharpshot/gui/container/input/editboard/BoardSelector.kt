package com.durhack.sharpshot.gui.container.input.editboard

import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.layout.Priority
import tornadofx.*

class BoardSelector : View() {
    val selected = SimpleBooleanProperty(false)

    override val root = pane {
        id = "Board Selector"
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS
        visibleWhen(selected)
    }
}
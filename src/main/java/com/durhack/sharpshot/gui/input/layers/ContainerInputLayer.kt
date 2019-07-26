package com.durhack.sharpshot.gui.input.layers

import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import tornadofx.*

class ContainerInputLayer : View() {

    override val root = stackpane {
        id = "Container Input Layer"

        // Prevent mouse buttons other than right click from panning the scroll pane
        addEventHandler(MouseEvent.MOUSE_PRESSED) {
            if (it.button != MouseButton.SECONDARY) {
                it.consume()
            }
        }
    }
}
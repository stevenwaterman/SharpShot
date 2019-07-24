package com.durhack.sharpshot.gui.container.input.layers

import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import tornadofx.*

class ContainerInputLayer : View() {
    private val singleNodeEdit: SingleNodeEdit by inject()

    override val root = stackpane {
        id = "Container Input Layer"
        add(singleNodeEdit)

        // Prevent mouse buttons other than right click from panning the scroll pane
        addEventHandler(MouseEvent.MOUSE_PRESSED) {
            if (it.button != MouseButton.SECONDARY) {
                it.consume()
            }
        }
    }
}
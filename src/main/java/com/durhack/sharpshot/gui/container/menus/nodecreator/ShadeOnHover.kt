package com.durhack.sharpshot.gui.container.menus.nodecreator

import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color

class ShadeOnHover(private val underneath: Node) : StackPane() {
    companion object {
        private val hoverColor = Color.color(0.1, 0.1, 0.2, 0.2)
        val hoverBackground = Background(BackgroundFill(hoverColor, CornerRadii.EMPTY, Insets.EMPTY))
    }

    init {
        this.addEventHandler(MouseEvent.ANY) { event ->
            if (event.eventType == MouseEvent.MOUSE_ENTERED) {
                background = hoverBackground
            }
            if (event.eventType == MouseEvent.MOUSE_EXITED) {
                background = Background.EMPTY
            }
            underneath.fireEvent(event)
        }
    }
}
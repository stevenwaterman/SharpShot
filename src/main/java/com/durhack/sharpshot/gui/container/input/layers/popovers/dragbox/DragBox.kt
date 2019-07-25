package com.durhack.sharpshot.gui.container.input.layers.popovers.dragbox

import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*

class DragBox : View() {
    override val root = pane {
        isVisible = false
        background = Background.EMPTY
        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths(1.0)))
    }
}
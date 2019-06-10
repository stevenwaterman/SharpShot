package com.durhack.sharpshot.gui.container.menus.nodecreator

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.geometry.Insets
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.*

class NodeButton<T: AbstractNode>(private val entry: RegistryEntry<T>, scale: Double, onHover: (RegistryEntry<T>) -> Unit, onClick: (RegistryEntry<T>) -> Unit): Fragment(){
    override val root = stackpane {
        add(entry.getGraphic(scale))
        add(ShadeOnHover())

        addEventHandler(MouseEvent.MOUSE_ENTERED){event ->
            onHover(entry)
        }

        addEventHandler(MouseEvent.MOUSE_PRESSED){event ->
            onClick(entry)
        }
    }
}

class ShadeOnHover : Pane() {
    companion object {
        private val hoverColor = Color.color(0.1, 0.1, 0.2, 0.2)
        private val hoverBackground = Background(BackgroundFill(hoverColor, CornerRadii.EMPTY, Insets.EMPTY))
    }

    init {
        addEventHandler(MouseEvent.MOUSE_ENTERED){ event ->
            background = hoverBackground
        }
        addEventHandler(MouseEvent.MOUSE_EXITED){ event ->
            background = Background.EMPTY
        }
    }
}
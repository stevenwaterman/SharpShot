package com.durhack.sharpshot.gui.container.menus.createnode

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.registry.NodeRegistry
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.geometry.Insets
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.*

class SelectNodeType(click: (RegistryEntry<out AbstractNode>) -> Unit,
                     hover: (RegistryEntry<out AbstractNode>) -> Unit) : Fragment() {
    private val perRow = 4
    private val scale = 24.0
    private val gap = 2.0
    private val outerPadding = 4.0

    override val root = gridpane {
        hgap = gap
        vgap = gap
        paddingAll = outerPadding

        NodeRegistry.entries.forEachIndexed { index, entry ->
            val column = index % perRow
            val row = index / perRow

            val button = NodeButton(entry, scale, { hover(entry) }, { click(entry) })
            add(button.root, column, row)
        }
    }
}

private class NodeButton<T : AbstractNode>(private val entry: RegistryEntry<T>,
                                   scale: Double,
                                   onHover: (RegistryEntry<T>) -> Unit,
                                   onClick: (RegistryEntry<T>) -> Unit) : Fragment() {
    override val root = stackpane {
        add(entry.getGraphic(scale))
        add(ShadeOnHover())

        addEventHandler(MouseEvent.MOUSE_ENTERED) { _ -> onHover(entry) }

        addEventHandler(MouseEvent.MOUSE_PRESSED) { event ->
            if (event.button == MouseButton.PRIMARY) {
                onClick(entry)
                event.consume()
            }
        }
    }
}

private class ShadeOnHover : Pane() {
    companion object {
        private val hoverColor = Color.color(0.1, 0.1, 0.2, 0.2)
        private val hoverBackground = Background(BackgroundFill(hoverColor, CornerRadii.EMPTY, Insets.EMPTY))
    }

    init {
        addEventHandler(MouseEvent.MOUSE_ENTERED) { _ -> background = hoverBackground }
        addEventHandler(MouseEvent.MOUSE_EXITED) { _ -> background = Background.EMPTY }
    }
}
package com.durhack.sharpshot.gui.container.menus.nodecreator

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.registry.NodeRegistry
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.input.MouseButton
import tornadofx.*

class NodeSelector(click: (RegistryEntry<out AbstractNode>) -> Unit, hover: (RegistryEntry<out AbstractNode>) -> Unit): Fragment(){
    private val perRow = 4
    private val scale = 24.0
    private val gap = 2.0
    private val outerPadding = 4.0

    override val root = gridpane {
        hgap = gap
        vgap = gap
        paddingAll = outerPadding

        NodeRegistry.entries.forEachIndexed { index, entry ->
            val graphic = entry.getGraphic(scale)

            graphic.setOnMouseClicked {
                if(it.button == MouseButton.PRIMARY){
                    click(entry)
                }
            }

            graphic.setOnMouseEntered {
                hover(entry)
            }

            val column = index % perRow
            val row = index / perRow
            add(graphic, column, row)
            add(ShadeOnHover(graphic), column, row)
        }
    }
}
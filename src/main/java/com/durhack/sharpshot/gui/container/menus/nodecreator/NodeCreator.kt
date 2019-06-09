package com.durhack.sharpshot.gui.container.menus.nodecreator

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.geometry.Insets
import javafx.scene.input.MouseButton
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*

class NodeCreator(click: (RegistryEntry<out AbstractNode>?) -> Unit): Fragment(){
    private val selector = NodeSelector(click) {hover(it)}
    private val info = NodeInfo()

    override val root = stackpane {
        hbox(8.0){
            add(selector)
            add(info)

            maxHeightProperty().bind(selector.root.heightProperty())
        }

        maxWidth = width
        maxHeight = height
        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(2.0)))
        background = Background(BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY))

        setOnMouseExited {
            if (it.target == this){
                click(null)
            }
        }

        setOnMousePressed {
            if(it.button == MouseButton.SECONDARY){
                click(null)
            }
        }

        setOnScroll {
            click(null)
        }
    }

    private fun hover(entry: RegistryEntry<out AbstractNode>){
        info.show(entry)
    }
}
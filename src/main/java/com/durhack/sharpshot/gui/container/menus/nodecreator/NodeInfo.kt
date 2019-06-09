package com.durhack.sharpshot.gui.container.menus.nodecreator

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.Border
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.text.Font
import tornadofx.*

class NodeInfo: Fragment(){
    private val titleSize = 18.0
    private val descriptionSize = 12.0
    private val gap = 4.0
    private val outerPadding = 4.0
    private val widthPx = 175.0
    private val graphicScale = 32.0

    private val graphicPane = stackpane {  }
    private val nodeName = Label().apply {
        text = ""
        font = Font(titleSize)
    }

    private val description = Label().apply {
        isWrapText = true
        font = Font.font(descriptionSize)
        background = Background.EMPTY
        border = Border.EMPTY

    }

    override val root = vbox(gap) {
        val h = hbox(gap) {
            add(graphicPane)
            stackpane {
                add(nodeName)
                StackPane.setAlignment(nodeName, Pos.CENTER)
                hgrow = Priority.ALWAYS
            }
        }
        add(description)
        description.maxWidthProperty().bind(h.widthProperty())

        paddingAll = outerPadding
        minWidth = widthPx
    }

    fun show(entry: RegistryEntry<out AbstractNode>) {
        graphicPane.clear()
        graphicPane.add(entry.getGraphic(graphicScale))

        nodeName.text = entry.name
        description.text = entry.description
    }
}
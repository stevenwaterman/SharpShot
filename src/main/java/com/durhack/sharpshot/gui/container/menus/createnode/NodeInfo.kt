package com.durhack.sharpshot.gui.container.menus.createnode

import com.durhack.sharpshot.gui.container.menus.createnode.nodebuttons.AbstractNodeButton
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.Border
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.text.Font
import tornadofx.*

class NodeInfo : Fragment() {
    private val titleSize = 18.0
    private val descriptionSize = 12.0
    private val gap = 4.0
    private val outerPadding = 4.0
    private val widthPx = 175.0
    private val graphicScale = 32

    private val graphicPane = stackpane { }
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

    fun show(button: AbstractNodeButton) {
        graphicPane.children.clear()
        graphicPane.add(button.graphicCreator(graphicScale))
        nodeName.text = button.name
        description.text = button.description
    }

    fun reset() {
        graphicPane.children.clear()
        nodeName.text = ""
        description.text = ""
    }
}
package com.durhack.sharpshot.gui

import com.durhack.sharpshot.core.nodes.INode
import com.durhack.sharpshot.core.nodes.NodeRegistry
import javafx.scene.control.Label
import javafx.scene.control.SelectionMode
import javafx.scene.control.Tooltip
import javafx.scene.layout.HBox
import tornadofx.*

internal class NodeCreator : View("Node Creator") {
    override val root = listview(NodeRegistry.nodeCreatorElements.observable()) {
        selectionModel.selectionMode = SelectionMode.SINGLE
        isEditable = false
    }

    fun createNode() = root.selectedItem?.create()
}

data class NodeCreatorElement(private val node: INode) :
        HBox(16.0, node.graphic(), Label(node.toString())) {

    init {
        Tooltip.install(this, Tooltip(node.tooltip))
    }

    fun create() = node.factory()
}
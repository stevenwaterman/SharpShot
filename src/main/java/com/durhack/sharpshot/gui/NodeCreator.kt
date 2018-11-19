package com.durhack.sharpshot.gui

import com.durhack.sharpshot.NodeRegistry
import com.durhack.sharpshot.nodes.INode
import javafx.collections.FXCollections
import javafx.scene.control.ListView

internal class NodeCreator : ListView<NodeTypeDescriptor>(FXCollections.observableArrayList()) {
    init {
        items.addAll(
                NodeRegistry.nodes.map {
                    NodeTypeDescriptor(it)
                }
                    )
    }

    fun createNode(): INode? {
        val descriptor = selectionModel.selectedItem
        return descriptor?.create()
    }
}

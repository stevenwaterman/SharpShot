package com.durhack.sharpshot.gui

import com.durhack.sharpshot.NodeRegistry
import javafx.scene.control.SelectionMode
import tornadofx.*

internal class NodeCreator : View("Node Creator") {
    override val root = listview(NodeRegistry.nodeCreatorElements.observable()){
        selectionModel.selectionMode = SelectionMode.SINGLE
        isEditable = false
    }

    fun createNode() = root.selectedItem?.create()
}

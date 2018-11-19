package com.durhack.sharpshot.gui

import com.durhack.sharpshot.nodes.INode
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.HBox

data class NodeTypeDescriptor(private val node: INode) : HBox(16.0,
                                                      node.graphic(),
                                                      Label(node.toString())) {
    init {
        val tooltip = Tooltip(node.tooltip)
        Tooltip.install(this, tooltip)
    }

    fun create() = node.factory()
}

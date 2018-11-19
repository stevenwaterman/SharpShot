package com.durhack.sharpshot.gui

import com.durhack.sharpshot.INode
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.HBox

data class NodeTypeDescriptor<T : INode>(val node: T, val tooltip: String, private val factory: () -> T?) : HBox(16.0,
                                                                                                                 node.toGraphic(),
                                                                                                                 Label(node.toString())) {
    init {
        val tooltip = Tooltip(tooltip)
        Tooltip.install(this, tooltip)
    }

    fun create() = factory()
}

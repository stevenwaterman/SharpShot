package com.durhack.sharpshot.gui.graphics

import com.durhack.sharpshot.core.state.Coordinate
import javafx.scene.Node
import javafx.scene.layout.Pane

abstract class Graphic(val coordinate: Coordinate, scale: Double, node: () -> Node) : Pane(node()) {
    init {
        layoutX = coordinate.x * scale - layoutBounds.minX
        layoutY = coordinate.y * scale - layoutBounds.minY
    }
}
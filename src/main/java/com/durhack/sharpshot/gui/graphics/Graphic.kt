package com.durhack.sharpshot.gui.graphics

import com.durhack.sharpshot.core.state.Coordinate
import javafx.scene.layout.Pane

abstract class Graphic(val coordinate: Coordinate,
                       scale: Int,
                       childWidth: Double = 1.0,
                       childHeight: Double = 1.0,
                       node: () -> Pane) : Pane(node()) {
    init {
        val paddingW = scale * (1 - childWidth) * 0.5
        val paddingH = scale * (1 - childHeight) * 0.5

        layoutX = coordinate.x * scale - layoutBounds.minX + paddingW
        layoutY = coordinate.y * scale - layoutBounds.minY + paddingH
    }
}
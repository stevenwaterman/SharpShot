package com.durhack.sharpshot.gui.graphics

import com.durhack.sharpshot.core.nodes.input.InNode
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.shapes.Triangle
import javafx.scene.paint.Color

class InNodeGraphic(coordinate: Coordinate, scale: Double, node: InNode): Graphic(coordinate, scale, {
    Triangle(scale, node.direction, Color.YELLOW, node.index?.toString() ?: "")
})
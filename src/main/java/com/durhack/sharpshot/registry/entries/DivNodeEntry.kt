package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.math.DivNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class DivNodeEntry() : AbstractNodeRegistryEntry<DivNode>(
        DivNode(Direction.UP),
        "Divide Node",
        "Divides the first bullet by the second"
                                                         ) {

    private val color = Color.ORANGE
    override fun draw(node: DivNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, color)
        Draw.text(gc, "÷", x, y, scale)
    }
}
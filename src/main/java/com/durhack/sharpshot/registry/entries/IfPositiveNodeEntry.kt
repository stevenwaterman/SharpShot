package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.conditional.IfPositiveNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class IfPositiveNodeEntry() : AbstractNodeRegistryEntry<IfPositiveNode>(
        IfPositiveNode(Direction.UP),
        "Branch if Positive Node",
        "Redirects all values >= 0"
                                                                       ) {

    private val color = Color.PINK
    override fun draw(node: IfPositiveNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, color)
        Draw.text(gc, "+", x, y, scale)
    }
}
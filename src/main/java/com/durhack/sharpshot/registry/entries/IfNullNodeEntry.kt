package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.conditional.IfNullNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class IfNullNodeEntry() : AbstractNodeRegistryEntry<IfNullNode>(
        IfNullNode(Direction.UP),
        "Branch if empty Node",
        "Redirects all empty bullets"
                                                               ) {

    private val color = Color.PINK
    override fun draw(node: IfNullNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, color)
        Draw.text(gc, "MT", x, y, scale)
    }
}
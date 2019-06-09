package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.conditional.IfPositiveNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class IfPositiveNodeEntry() : RegistryEntry<IfPositiveNode>(
        IfPositiveNode(Direction.UP),
        "If Positive",
        "Redirects all values >= 0"
                                                           ) {

    override fun draw(node: IfPositiveNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, Color.PINK)
        Draw.text(gc, "+", x, y, scale)
    }
}
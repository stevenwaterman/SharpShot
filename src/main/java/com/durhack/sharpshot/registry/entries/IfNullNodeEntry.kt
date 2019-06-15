package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.conditional.IfNullNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class IfNullNodeEntry() : RegistryEntry<IfNullNode>(
        IfNullNode(Direction.UP),
        "If Empty",
        "Redirects all empty bullets"
                                                   ) {

    override fun draw(node: IfNullNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Int) {
        Draw.triangle(gc, node.direction, x, y, scale, Color.LIGHTSKYBLUE)
        Draw.text(gc, "E", x, y, scale)
    }
}
package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.VoidNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class VoidNodeEntry() : AbstractNodeRegistryEntry<VoidNode>(
        VoidNode(Direction.UP),
        "Void Node",
        "Blocks bullets"
                                                           ) {

    private val color = Color.BLACK
    override fun draw(node: VoidNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.circle(gc, x, y, scale, color)
    }
}
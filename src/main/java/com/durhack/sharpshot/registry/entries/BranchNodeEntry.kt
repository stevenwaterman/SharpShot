package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.BranchNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class BranchNodeEntry() : AbstractNodeRegistryEntry<BranchNode>(
        BranchNode(Direction.UP),
        "Branch Node",
        "Redirects all bullets"
                                                               ) {

    private val color = Color.PINK

    override fun draw(node: BranchNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, color)
    }
}
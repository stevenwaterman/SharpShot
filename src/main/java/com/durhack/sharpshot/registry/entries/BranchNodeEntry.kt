package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.BranchNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class BranchNodeEntry() : RegistryEntry<BranchNode>(
        BranchNode(Direction.UP),
        "Branch",
        "Redirects all bullets"
                                                   ) {

    override fun draw(node: BranchNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Int) {
        Draw.triangle(gc, node.direction, x, y, scale, Color.BLACK)
    }
}
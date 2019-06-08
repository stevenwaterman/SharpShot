package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.conditional.IfNullNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext

class IfNullNodeEntry() : AbstractNodeRegistryEntry<IfNullNode>(
        IfNullNode(Direction.UP),
        "Branch if empty Node",
        "Redirects all empty bullets"
                                                               ) {

    override fun draw(node: IfNullNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
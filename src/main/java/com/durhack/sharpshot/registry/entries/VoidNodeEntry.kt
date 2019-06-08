package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.VoidNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext

class VoidNodeEntry() : AbstractNodeRegistryEntry<VoidNode>(
        VoidNode(Direction.UP),
        "Void Node",
        "Blocks bullets"
                                                           ) {

    override fun draw(node: VoidNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
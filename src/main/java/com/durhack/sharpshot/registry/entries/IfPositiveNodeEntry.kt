package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.conditional.IfPositiveNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext

class IfPositiveNodeEntry() : AbstractNodeRegistryEntry<IfPositiveNode>(
        IfPositiveNode(Direction.UP),
        "Branch if Positive Node",
        "Redirects all values >= 0"
                                                                       ) {

    override fun draw(node: IfPositiveNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
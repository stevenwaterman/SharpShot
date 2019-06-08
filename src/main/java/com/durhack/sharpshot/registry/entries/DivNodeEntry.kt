package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.math.DivNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext

class DivNodeEntry() : AbstractNodeRegistryEntry<DivNode>(
        DivNode(Direction.UP),
        "Divide Node",
        "Divides the first bullet by the second"
                                                         ) {

    override fun draw(node: DivNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.math.SubNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext

class SubNodeEntry() : AbstractNodeRegistryEntry<SubNode>(
        SubNode(Direction.UP),
        "Subtract Node",
        "Subtracts the first bullet from the second"
                                                         ) {

    override fun draw(node: SubNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
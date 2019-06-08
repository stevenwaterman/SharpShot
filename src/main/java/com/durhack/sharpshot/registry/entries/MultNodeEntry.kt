package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.math.MultNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext

class MultNodeEntry() : AbstractNodeRegistryEntry<MultNode>(
        MultNode(Direction.UP),
        "Multiply Node",
        "Multiplies two bullets"
                                                           ) {

    override fun draw(node: MultNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.other.RandomNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext

class RandomNodeEntry() : AbstractNodeRegistryEntry<RandomNode>(
        RandomNode(Direction.UP),
        "Random Node",
        "Outputs a random value up to the bullet value (inclusive)"
                                                               ) {

    override fun draw(node: RandomNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.SplitterNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext

class SplitterNodeEntry() : AbstractNodeRegistryEntry<SplitterNode>(
        SplitterNode(Direction.UP),
        "Splitter Node",
        "When a bullet passes through, 2 more come out the sides"
                                                                   ) {

    override fun draw(node: SplitterNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
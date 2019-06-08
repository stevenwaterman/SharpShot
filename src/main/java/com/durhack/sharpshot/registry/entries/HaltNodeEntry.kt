package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.other.HaltNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext

class HaltNodeEntry() : AbstractNodeRegistryEntry<HaltNode>(
        HaltNode(Direction.UP),
        "Halt Node",
        "Terminates the program"
                                                           ) {

    override fun draw(node: HaltNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
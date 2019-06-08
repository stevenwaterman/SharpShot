package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.other.StackNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext

class StackNodeEntry() : AbstractNodeRegistryEntry<StackNode>(
        StackNode(Direction.UP),
        "Stack Node",
        "Inputs in the back pop from the stack, inputs to other sides push to the stack"
                                                             ) {

    override fun draw(node: StackNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.input.ListNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext

class ListNodeEntry() : AbstractNodeRegistryEntry<ListNode>(
        ListNode(Direction.UP),
        "List Node",
        "Every time a bullet comes in, outputs the next value in the list of inputs"
                                                           ) {

    override fun draw(node: ListNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
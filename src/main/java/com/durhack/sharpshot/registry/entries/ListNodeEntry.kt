package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.input.ListNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class ListNodeEntry() : AbstractNodeRegistryEntry<ListNode>(
        ListNode(Direction.UP),
        "List Node",
        "Every time a bullet comes in, outputs the next value in the list of inputs"
                                                           ) {

    private val color = Color.YELLOW
    override fun draw(node: ListNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, color)
        Draw.text(gc, "LST", x, y, scale)
    }
}
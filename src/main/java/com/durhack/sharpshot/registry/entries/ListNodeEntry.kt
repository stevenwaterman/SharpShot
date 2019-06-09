package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.input.ListNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class ListNodeEntry() : RegistryEntry<ListNode>(
        ListNode(Direction.UP),
        "List",
        "Every time a bullet comes in, outputs the next value in the list of inputs"
                                               ) {

    override fun draw(node: ListNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, Color.YELLOW)
        Draw.text(gc, "LST", x, y, scale)
    }
}
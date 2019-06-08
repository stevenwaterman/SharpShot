package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.other.StackNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class StackNodeEntry() : AbstractNodeRegistryEntry<StackNode>(
        StackNode(Direction.UP),
        "Stack Node",
        "Inputs in the back pop from the stack, inputs to other sides push to the stack"
                                                             ) {

    private val color = Color.YELLOW
    override fun draw(node: StackNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, color)
        Draw.text(gc, "S${node.stackSize}", x, y, scale)
    }
}
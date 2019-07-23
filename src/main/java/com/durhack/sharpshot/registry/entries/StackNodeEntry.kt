package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.memory.StackNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class StackNodeEntry : RegistryEntry<StackNode>(
        StackNode(Direction.UP),
        "Stack",
        "Inputs in the back pop from the stack, inputs to other sides push to the stack"
                                               ) {

    override fun draw(node: StackNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Int) {
        Draw.triangle(gc, node.direction, x, y, scale, Color.CRIMSON)
        Draw.text(gc, "S${node.stackSize}", x, y, scale)
    }
}
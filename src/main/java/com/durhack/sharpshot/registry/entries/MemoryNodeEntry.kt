package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.memory.MemoryNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class MemoryNodeEntry() : RegistryEntry<MemoryNode>(
        MemoryNode(Direction.UP),
        "Memory",
        "Inputs from the back output the currently stored value, inputs to the side change the stored value"
                                                   ) {

    override fun draw(node: MemoryNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Int) {
        Draw.triangle(gc, node.direction, x, y, scale, Color.CRIMSON)
        Draw.text(gc, node.storedValue?.toString() ?: "E", x, y, scale)
    }
}
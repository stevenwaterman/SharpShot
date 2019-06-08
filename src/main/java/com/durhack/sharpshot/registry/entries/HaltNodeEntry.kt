package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.other.HaltNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class HaltNodeEntry() : AbstractNodeRegistryEntry<HaltNode>(
        HaltNode(Direction.UP),
        "Halt Node",
        "Terminates the program"
                                                           ) {

    private val color = Color.RED
    override fun draw(node: HaltNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.circle(gc, x, y, scale, color)
    }
}
package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.other.HaltNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class HaltNodeEntry() : RegistryEntry<HaltNode>(
        HaltNode(Direction.UP),
        "Halt",
        "Terminates the program"
                                               ) {

    override fun draw(node: HaltNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Int) {
        Draw.circle(gc, x, y, scale, Color.CRIMSON)
        Draw.text(gc, "H", x, y, scale, Color.WHITE)
    }
}
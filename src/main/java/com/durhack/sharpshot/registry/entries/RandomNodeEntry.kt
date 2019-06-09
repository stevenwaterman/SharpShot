package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.other.RandomNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class RandomNodeEntry() : RegistryEntry<RandomNode>(
        RandomNode(Direction.UP),
        "Random",
        "Outputs a random value up to the bullet value (inclusive)"
                                                   ) {

    override fun draw(node: RandomNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, Color.GREEN)
        Draw.text(gc, "?", x, y, scale)
    }
}
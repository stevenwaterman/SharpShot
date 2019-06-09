package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.SplitterNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class SplitterNodeEntry() : RegistryEntry<SplitterNode>(
        SplitterNode(Direction.UP),
        "Splitter",
        "When a bullet passes through, 2 more come out the sides"
                                                       ) {

    override fun draw(node: SplitterNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.diamond(gc, node.direction, x, y, scale, Color.YELLOW)
    }
}
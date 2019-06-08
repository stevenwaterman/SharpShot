package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.math.AddNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class AddNodeEntry() : AbstractNodeRegistryEntry<AddNode>(
        AddNode(Direction.UP),
        "Add Node",
        "Adds two bullets together"
                                                         ) {

    private val color = Color.YELLOW

    override fun draw(node: AddNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, color)
        Draw.text(gc, "+", x, y, scale)
    }
}
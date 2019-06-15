package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.math.AddNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class AddNodeEntry() : RegistryEntry<AddNode>(
        AddNode(Direction.UP),
        "Add",
        "Adds two bullets together"
                                             ) {

    override fun draw(node: AddNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Int) {
        Draw.triangle(gc, node.direction, x, y, scale, Color.PLUM)
        Draw.text(gc, "+", x, y, scale)
    }
}
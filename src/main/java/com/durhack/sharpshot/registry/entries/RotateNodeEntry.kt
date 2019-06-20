package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.RotateNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class RotateNodeEntry() : RegistryEntry<RotateNode>(
        RotateNode(Direction.UP),
        "Rotate",
        "When a bullet enters, it turns 90 degrees clockwise"
                                                       ) {
    override fun draw(node: RotateNode, gc: GraphicsContext, x: Double, y: Double, scale: Int) {
        Draw.rotate(gc, node.direction, x, y, scale, Color.BLACK)
        Draw.text(gc, "C", x, y, scale, Color.BLACK)
    }
}
package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.RotateAntiNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class RotateAntiNodeEntry() : RegistryEntry<RotateAntiNode>(
        RotateAntiNode(Direction.UP),
        "Rotate ACW",
        "When a bullet enters, it turns 90 degrees anticlockwise"
                                                           ) {
    override fun draw(node: RotateAntiNode, gc: GraphicsContext, x: Double, y: Double, scale: Int) {
        Draw.rotateAnti(gc, node.direction, x, y, scale, Color.BLACK)
        Draw.text(gc, "A", x, y, scale, Color.BLACK)
    }
}
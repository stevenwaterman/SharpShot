package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.routing.SingleUseBranchNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class SingleUseBranchNodeEntry() : RegistryEntry<SingleUseBranchNode>(
        SingleUseBranchNode(Direction.UP),
        "Single-use Branch",
        "Redirects the first bullet only"
                                                                     ) {

    override fun draw(node: SingleUseBranchNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Int) {
        if(node.enabled){
            Draw.triangle(gc, node.direction, x, y, scale, Color.DARKORANGE)
        }
        else{
            Draw.triangle(gc, node.direction, x, y, scale, Color.GRAY)
        }
    }
}
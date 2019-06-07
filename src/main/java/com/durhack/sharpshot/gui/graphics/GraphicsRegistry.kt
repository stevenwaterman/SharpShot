package com.durhack.sharpshot.gui.graphics

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.input.InNode
import com.durhack.sharpshot.core.state.Coordinate

object GraphicsRegistry{
    fun getGraphic(coordinate: Coordinate, scale: Double, node: AbstractNode): Graphic{
        return when(node){
            is InNode -> InNodeGraphic(coordinate, scale, node)
            else -> throw IllegalArgumentException("Unrecognised node: $node")
        }
    }
}
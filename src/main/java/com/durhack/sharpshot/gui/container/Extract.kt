package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.util.CoordinateRange2D

class Extract(allNodes: Map<Coordinate, AbstractNode>, range: CoordinateRange2D) {
    private constructor(extractNodes: Map<Coordinate, AbstractNode>, width: Int, height: Int) :
            this(extractNodes, CoordinateRange2D(width, height))

    val nodes: Map<Coordinate, AbstractNode>
    val width: Int
    val height: Int

    val isEmpty: Boolean get() = nodes.isEmpty()

    init {
        //Normalisation
        nodes = allNodes.filterKeys { it in range }
                .mapKeys { (coord, _) -> coord - range.low }

        width = range.width
        height = range.height
    }

    val mirroredHorizontal: Extract
        get() {
            val newNodes =
                    nodes.mapKeys { (coord, _) ->
                        val newX = width - coord.x - 1
                        return@mapKeys Coordinate(newX, coord.y)
                    }.mapValues { (_, node) -> node.mirroredHorizontal }
            return Extract(newNodes, width, height)
        }

    val mirroredVertical: Extract
        get() {
            val newNodes =
                    nodes.mapKeys { (coord, _) ->
                        val newY = height - coord.y - 1
                        return@mapKeys Coordinate(coord.x, newY)
                    }.mapValues { (_, node) -> node.mirroredVertical }
            return Extract(newNodes, width, height)
        }

    val rotatedClockwise: Extract
        get() {
        val newNodes = nodes.mapKeys { (coord, _) ->
            val newX = height - coord.y - 1
            val newY = coord.x
            return@mapKeys Coordinate(newX, newY)
        }.mapValues { (_, node) -> node.clockwise }

        val oldWidth = width
        val oldHeight = height
            val newWidth = oldHeight
            val newHeight = oldWidth

            return Extract(newNodes, newWidth, newHeight)
    }

    val trimmed: Extract
        get() {
            val minX = nodes.keys.map(Coordinate::x).min()!!
            val minY = nodes.keys.map(Coordinate::y).min()!!
            val maxX = nodes.keys.map(Coordinate::x).max()!!
            val maxY = nodes.keys.map(Coordinate::y).max()!!
            return Extract(nodes, CoordinateRange2D(minX..maxX, minY..maxY))
        }
}
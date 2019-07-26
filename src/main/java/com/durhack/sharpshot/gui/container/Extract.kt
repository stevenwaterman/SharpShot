package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.util.CoordinateRange2D

class Extract(allNodes: Map<Coordinate, AbstractNode>, range: CoordinateRange2D) {
    val isEmpty: Boolean get() = nodes.isEmpty()
    var nodes: Map<Coordinate, AbstractNode> private set
    var width: Int private set
    var height: Int private set

    init {
        //Normalisation
        nodes = allNodes.filterKeys { it in range }
                .mapKeys { (coord, _) -> coord - range.low }

        width = range.width
        height = range.height
    }

    fun mirrorHorizontal() {
        val newNodes =
                nodes.mapKeys { (coord, _) ->
                    val newX = width - coord.x - 1
                    return@mapKeys Coordinate(newX, coord.y)
                }
        newNodes.values.forEach { it.direction = it.direction.mirrorHorizontal }
        nodes = newNodes
    }

    fun mirrorVertical() {
        val newNodes =
                nodes.mapKeys { (coord, _) ->
                    val newY = height - coord.y - 1
                    return@mapKeys Coordinate(coord.x, newY)
                }
        newNodes.values.forEach { it.direction = it.direction.mirrorVertical }
        nodes = newNodes
    }

    fun rotateClockwise() {
        val newNodes = nodes.mapKeys { (coord, _) ->
            val newX = height - coord.y - 1
            val newY = coord.x
            return@mapKeys Coordinate(newX, newY)
        }
        newNodes.forEach { (_, node) -> node.clockwise() }
        nodes = newNodes

        val oldWidth = width
        val oldHeight = height
        width = oldHeight
        height = oldWidth
    }

    fun trim() {
        val minX = nodes.keys.map(Coordinate::x).min()!!
        val minY = nodes.keys.map(Coordinate::y).min()!!
        val minCoord = Coordinate(minX, minY)
        nodes = nodes.mapKeys { (coord, _) -> coord - minCoord }
        val maxX = nodes.keys.map(Coordinate::x).max()!!
        val maxY = nodes.keys.map(Coordinate::y).max()!!
        width = maxX + 1
        height = maxY + 1
    }
}
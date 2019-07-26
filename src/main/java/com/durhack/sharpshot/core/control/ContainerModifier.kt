package com.durhack.sharpshot.core.control

import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.Extract
import com.durhack.sharpshot.gui.util.CoordinateRange2D
import com.durhack.sharpshot.util.globalContainer

object ContainerModifier {
    private val fullRange: CoordinateRange2D
        get() = CoordinateRange2D(0 until globalContainer.width,
                                  0 until globalContainer.height)
    private val minCoord = Coordinate(0, 0)

    /**
     * Increasing size RIGHT means adding a column on the rightmost edge
     */
    fun increaseSize(direction: Direction) {
        var extract: Extract? = null
        if (direction.deltaX < 0 || direction.deltaY < 0) {
            extract = cut(fullRange)
        }

        if (direction.deltaX != 0) globalContainer.width++
        if (direction.deltaY != 0) globalContainer.height++

        if (extract != null) {
            val placeLocation = minCoord - direction
            paste(extract, placeLocation)
        }
    }

    fun canDecreaseSize(direction: Direction) =
            when (direction) {
                Direction.UP -> globalContainer.nodes.keys.none { it.y == globalContainer.height - 1 } && globalContainer.height > 1
                Direction.LEFT -> globalContainer.nodes.keys.none { it.x == globalContainer.width - 1 } && globalContainer.width > 1
                Direction.DOWN -> globalContainer.nodes.keys.none { it.y == 0 } && globalContainer.height > 1
                Direction.RIGHT -> globalContainer.nodes.keys.none { it.x == 0 } && globalContainer.width > 1
            }

    /**
     * Decreasing size RIGHT means removing the leftmost column
     */
    fun decreaseSize(direction: Direction) {
        var extract: Extract? = null
        if (direction.deltaX > 0 || direction.deltaY > 0) {
            extract = cut(fullRange)
        }

        if (direction.deltaX != 0) globalContainer.width--
        if (direction.deltaY != 0) globalContainer.height--

        if (extract != null) {
            val placeLocation = minCoord - direction
            paste(extract, placeLocation)
        }
    }

    fun copy(range: CoordinateRange2D) = Extract(globalContainer.nodes, range)

    fun clear(range: CoordinateRange2D) {
        globalContainer.nodes.keys.removeAll { range.contains(it) }
    }

    fun cut(range: CoordinateRange2D): Extract {
        val extract = copy(range)
        clear(range)
        return extract
    }

    fun canPlace(extract: Extract, location: Coordinate): Boolean {
        val extractNodes = extract.nodes.keys.map { it + location }.toSet()
        val containerNodes = globalContainer.nodes.keys.toSet()
        val overlap = extractNodes.intersect(containerNodes)
        return overlap.isEmpty()
    }

    fun paste(extract: Extract, location: Coordinate) {
        val extractNodes = extract.nodes
                .mapKeys { (relativeCoord, _) -> relativeCoord + location }
        extractNodes.forEach { absCoord, node -> globalContainer.nodes[absCoord] = node }
    }
}
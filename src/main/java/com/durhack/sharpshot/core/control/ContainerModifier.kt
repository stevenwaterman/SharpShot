package com.durhack.sharpshot.core.control

import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.Extract
import com.durhack.sharpshot.gui.util.CoordinateRange2D
import com.durhack.sharpshot.util.container

object ContainerModifier {
    private val fullRange: CoordinateRange2D
        get() = CoordinateRange2D(0 until container.width,
                                  0 until container.height)
    private val minCoord = Coordinate(0, 0)

    /**
     * Increasing size RIGHT means adding a column on the rightmost edge
     */
    fun increaseSize(direction: Direction) {
        var extract: Extract? = null
        if (direction.deltaX < 0 || direction.deltaY < 0) {
            extract = cut(fullRange)
        }

        if (direction.deltaX != 0) container.width++
        if (direction.deltaY != 0) container.height++

        if (extract != null) {
            val placeLocation = minCoord - direction
            paste(extract, placeLocation)
        }
    }

    fun canDecreaseSize(direction: Direction) =
            when (direction) {
                Direction.UP -> container.nodes.keys.none { it.y == container.height - 1 } && container.height > 1
                Direction.LEFT -> container.nodes.keys.none { it.x == container.width - 1 } && container.width > 1
                Direction.DOWN -> container.nodes.keys.none { it.y == 0 } && container.height > 1
                Direction.RIGHT -> container.nodes.keys.none { it.x == 0 } && container.width > 1
            }

    /**
     * Decreasing size RIGHT means removing the leftmost column
     */
    fun decreaseSize(direction: Direction) {
        var extract: Extract? = null
        if (direction.deltaX > 0 || direction.deltaY > 0) {
            extract = cut(fullRange)
        }

        if (direction.deltaX != 0) container.width--
        if (direction.deltaY != 0) container.height--

        if (extract != null) {
            val placeLocation = minCoord - direction
            paste(extract, placeLocation)
        }
    }

    fun copy(range: CoordinateRange2D) = Extract(container.nodes, range)

    fun clear(range: CoordinateRange2D) {
        container.nodes.keys.removeAll { range.contains(it) }
    }

    fun cut(range: CoordinateRange2D): Extract {
        val extract = copy(range)
        clear(range)
        return extract
    }

    fun canPlace(extract: Extract, location: Coordinate): Boolean {
        val extractNodes = extract.nodes.keys.map { it + location }.toSet()
        val containerNodes = container.nodes.keys.toSet()
        val overlap = extractNodes.intersect(containerNodes)
        return overlap.isEmpty()
    }

    fun paste(extract: Extract, location: Coordinate) {
        val extractNodes = extract.nodes
                .mapKeys { (relativeCoord, _) -> relativeCoord + location }
        extractNodes.forEach { absCoord, node -> container.nodes[absCoord] = node }
    }
}
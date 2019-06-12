package com.durhack.sharpshot.core.control

import com.durhack.sharpshot.core.state.Container
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.Extract

fun Container.minCoord() = Coordinate(0, 0)
fun Container.maxCoord() = Coordinate(width - 1, height - 1)

/**
 * Increasing size RIGHT means adding a column on the rightmost edge
 */
fun Container.increaseSize(direction: Direction) {
    var extract: Extract? = null
    if (direction.deltaX < 0 || direction.deltaY < 0) {
        extract = cut()
    }

    if (direction.deltaX != 0) width++
    if (direction.deltaY != 0) height++

    if (extract != null) {
        val placeLocation = minCoord() - direction
        paste(extract, placeLocation)
    }
}

fun Container.canDecreaseSize(direction: Direction) =
        when (direction) {
            Direction.UP -> nodes.keys.none { it.y == height - 1 }
            Direction.LEFT -> nodes.keys.none { it.x == width - 1 }
            Direction.DOWN -> nodes.keys.none { it.y == 0 }
            Direction.RIGHT -> nodes.keys.none { it.x == 0 }
        }

/**
 * Decreasing size RIGHT means removing the leftmost column
 */
fun Container.decreaseSize(direction: Direction) {
    var extract: Extract? = null
    if (direction.deltaX > 0 || direction.deltaY > 0) {
        extract = cut()
    }

    if (direction.deltaX != 0) width--
    if (direction.deltaY != 0) height--

    if (extract != null) {
        val placeLocation = minCoord() - direction
        paste(extract, placeLocation)
    }
}

fun Container.copy(low: Coordinate, high: Coordinate) = Extract(nodes, low, high)

fun Container.clear(low: Coordinate, high: Coordinate) {
    nodes.keys.removeAll { it.inside(low, high) }
}

fun Container.cut(low: Coordinate = minCoord(), high: Coordinate = maxCoord()): Extract {
    val extract = copy(low, high)
    clear(low, high)
    return extract
}

fun Container.canPlace(extract: Extract, location: Coordinate): Boolean {
    val extractNodes = extract.nodes.keys.map { it + location }.toSet()
    val containerNodes = nodes.keys.toSet()
    val overlap = extractNodes.intersect(containerNodes)
    return overlap.isEmpty()
}

fun Container.paste(extract: Extract, location: Coordinate) {
    val extractNodes = extract.nodes
            .mapKeys { (relativeCoord, _) -> relativeCoord + location }
    extractNodes.forEach { absCoord, node -> nodes[absCoord] = node }
}

private fun sortCoords(c1: Coordinate, c2: Coordinate): Pair<Coordinate, Coordinate> {
    val lowX = Integer.min(c1.x, c2.x)
    val lowY = Integer.min(c1.y, c2.y)
    val low = Coordinate(lowX, lowY)

    val highX = Integer.max(c1.x, c2.x)
    val highY = Integer.max(c1.y, c2.y)
    val high = Coordinate(highX, highY)

    return low to high
}
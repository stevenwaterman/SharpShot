package com.durhack.sharpshot.gui.util

import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.util.clamp

data class CoordinateRange2D(val xRange: IntRange, val yRange: IntRange) {
    constructor(width: Int, height: Int) : this(0 until width, 0 until height)

    operator fun contains(it: Coordinate): Boolean {
        return it.x in xRange && it.y in yRange
    }

    fun clamp(coord: Coordinate): Coordinate {
        val clampedX = coord.x.clamp(low.x, high.x)
        val clampedY = coord.y.clamp(low.y, high.y)
        return Coordinate(clampedX, clampedY)
    }

    val width = xRange.range
    val height = yRange.range

    val low = Coordinate(xRange.start, yRange.start)
    val high = Coordinate(xRange.endInclusive, yRange.endInclusive)
}
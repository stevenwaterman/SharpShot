package com.durhack.sharpshot.gui.util

import com.durhack.sharpshot.core.state.Coordinate

data class CoordinateRange2D(val xRange: IntRange, val yRange: IntRange) {
    operator fun contains(it: Coordinate): Boolean {
        return it.x in xRange && it.y in yRange
    }

    val width = xRange.endInclusive - xRange.start + 1
    val height = yRange.endInclusive - yRange.start + 1

    val low = Coordinate(xRange.start, yRange.start)
    val high = Coordinate(xRange.endInclusive, yRange.endInclusive)
}
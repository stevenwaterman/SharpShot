package com.durhack.sharpshot.gui.util

import com.durhack.sharpshot.core.state.Coordinate

data class FractionalCoordinate(val x: Double, val y: Double) {
    val validInGlobalContainer: Coordinate?
        get() {
            val coord = Coordinate(x.toInt(), y.toInt())
            return when {
                coord.existsInGlobalContainer -> coord
                else -> null
            }
        }

    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())

    val floor: Coordinate by lazy { Coordinate(x.toInt(), y.toInt()) }
}
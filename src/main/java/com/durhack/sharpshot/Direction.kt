package com.durhack.sharpshot

import java.util.ArrayList

enum class Direction private constructor(val deltaX: Int, val deltaY: Int, val degrees: Double) {
    UP(0, -1, 0.0),
    RIGHT(1, 0, 90.0),
    DOWN(0, 1, 180.0),
    LEFT(-1, 0, 270.0);

    fun antiClockwise(): Direction {
        return Direction.clockwiseOf(Direction.clockwiseOf(Direction.clockwiseOf(this)))
    }

    fun clockwise(): Direction {
        return Direction.clockwiseOf(this)
    }

    companion object {

        fun clockwiseOf(d: Direction): Direction {
            return when (d) {
                UP -> Direction.RIGHT
                RIGHT -> Direction.DOWN
                DOWN -> Direction.LEFT
                LEFT -> Direction.UP
            }
        }

        fun inverseOf(d: Direction): Direction {
            return Direction.clockwiseOf(Direction.clockwiseOf(d))
        }

        fun others(d: Direction): List<Direction> {
            //TODO fix
            var d = d
            val dirs = ArrayList<Direction>()
            for (i in 0..2) {
                d = Direction.clockwiseOf(d)
                dirs.add(d)
            }

            return dirs
        }
    }
}
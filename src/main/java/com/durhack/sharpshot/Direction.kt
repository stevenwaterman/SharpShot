package com.durhack.sharpshot

enum class Direction(val deltaX: Int, val deltaY: Int, val quarters: Int) {
    UP(0, -1, 0),
    RIGHT(1, 0, 1),
    DOWN(0, 1, 2),
    LEFT(-1, 0, 3);

    fun plusQuarters(add: Int): Direction {
        return ofQuarters(quarters + add)
    }

    val inverse get() = plusQuarters(2)
    val clockwise get() = plusQuarters(1)
    val antiClockwise get() = plusQuarters(-1)
    val others get() = (1..3).map { plusQuarters(it) }
    val degrees get() = quarters * 90.0

    companion object{
        fun ofQuarters(quarters: Int): Direction {
            val adjusted = (quarters + 4) % 4

            return when (adjusted) {
                0    -> Direction.UP
                1    -> Direction.RIGHT
                2    -> Direction.DOWN
                3    -> Direction.LEFT
                else -> throw IllegalArgumentException("Quarters must be 0-3")
            }
        }
    }
}
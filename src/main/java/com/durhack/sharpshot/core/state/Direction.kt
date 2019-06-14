package com.durhack.sharpshot.core.state

enum class Direction(val deltaX: Int, val deltaY: Int, val quarters: Int) {
    UP(0, -1, 0),
    RIGHT(1, 0, 1),
    DOWN(0, 1, 2),
    LEFT(-1, 0, 3);

    private fun plusQuarters(add: Int): Direction = ofQuarters(quarters + add)

    operator fun plus(oth: Direction): Direction = plusQuarters(oth.quarters)
    operator fun minus(oth: Direction): Direction = plusQuarters(-oth.quarters)

    val inverse get() = plusQuarters(2)
    val clockwise get() = plusQuarters(1)
    val antiClockwise get() = plusQuarters(-1)
    val mirrorHorizontal get() = if(deltaX != 0) inverse else this
    val mirrorVertical get() = if(deltaY != 0) inverse else this
    val others get() = (1..3).map(::plusQuarters)
    val degrees get() = quarters * 90.0

    companion object {
        fun ofQuarters(quarters: Int): Direction {
            val adjusted = (quarters + 4) % 4

            return when (adjusted) {
                0 -> UP
                1 -> RIGHT
                2 -> DOWN
                3 -> LEFT
                else -> throw IllegalArgumentException("Quarters must be 0-3")
            }
        }
    }
}
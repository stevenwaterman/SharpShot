package com.durhack.sharpshot


import java.util.*

/**
 * This is an immutable class.
 */
class Coordinate(val x: Int, val y: Int) {

    operator fun plus(direction: Direction): Coordinate {
        val newX = x + direction.deltaX
        val newY = y + direction.deltaY
        return Coordinate(newX, newY)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Coordinate?
        return x == that!!.x && y == that.y
    }

    override fun hashCode(): Int {
        return Objects.hash(x, y)
    }

    fun wrap(width: Int, height: Int): Coordinate {
        return Coordinate((x + width) % width, (y + height) % height)
    }
}

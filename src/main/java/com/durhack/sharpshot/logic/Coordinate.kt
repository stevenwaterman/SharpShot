package com.durhack.sharpshot.logic


import com.google.gson.JsonElement
import com.google.gson.JsonObject

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

    override fun hashCode() = 31 * x + y //TODO this could be improved with rot16

    fun toJson(): JsonElement {
        val json = JsonObject()
        json.addProperty("x", x)
        json.addProperty("y", y)
        return json
    }

    operator fun plus(oth: Coordinate) = Coordinate(x + oth.x, y + oth.y)
    override fun toString() = "Coordinate(x=$x, y=$y)"

    companion object {
        fun fromJson(json: JsonObject): Coordinate {
            val x = json.get("x").asInt
            val y = json.get("y").asInt
            return Coordinate(x, y)
        }
    }
}

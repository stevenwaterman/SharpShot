package com.durhack.sharpshot.logic

class Movement(val from: Coordinate, val to: Coordinate)

class SwapChecker(val c1: Coordinate, val c2: Coordinate) {

    constructor(movement: Movement) : this(movement.from, movement.to)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SwapChecker
        return c1 + c2 == other.c1 + other.c2
    }

    override fun hashCode(): Int {
        return c1.hashCode() + c2.hashCode()
    }
}
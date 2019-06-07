package com.durhack.sharpshot.core.state

class SwapChecker(val movement: Movement) {
    private val c1 = movement.from
    private val c2 = movement.to

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SwapChecker
        return c1 + c2 == other.c1 + other.c2
    }

    override fun hashCode(): Int = c1.hashCode() + c2.hashCode()
}
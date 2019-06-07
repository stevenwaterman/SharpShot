package com.durhack.sharpshot.core.state

import java.math.BigInteger

/**
 * Immutable
 */
data class Bullet(val coordinate: Coordinate, val direction: Direction, val value: BigInteger?) {
    fun increment() = Bullet(nextCoord(), direction, value)
    fun nextCoord() = coordinate + direction

    override fun toString() = "Bullet(coordinate = $coordinate, direction=$direction, value=$value)"
}

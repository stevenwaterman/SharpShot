package com.durhack.sharpshot.util

import javafx.geometry.Point2D
import java.math.BigInteger
import kotlin.math.max
import kotlin.math.min

fun Char.asBigInteger() = BigInteger(toInt().toString())
fun BigInteger.asChar() = toInt().toChar()

inline fun <reified K, reified R> Map<K, *>.filterType(): Map<K, R> {
    val dest = mutableMapOf<K, R>()
    for ((key, value) in this) if (value is R) dest.put(key, value)
    return dest
}

fun <T> Iterable<T>.pairDuplicates(): List<Pair<T, T>> {
    val seen = mutableMapOf<T, T>()
    val pairs = mutableListOf<Pair<T, T>>()

    forEach { elem ->
        val oth = seen[elem]
        if (oth == null) seen[elem] = elem
        else pairs.add(elem to oth)
    }

    return pairs
}

fun Double.clamp(min: Number, max: Number) = min(max.toDouble(), max(min.toDouble(), this))
fun Int.clamp(min: Int, max: Int) = min(max, max(min, this))
operator fun Point2D.plus(oth: Point2D): Point2D = add(oth)
operator fun Point2D.minus(oth: Point2D): Point2D = subtract(oth)
operator fun Point2D.times(oth: Double): Point2D = multiply(oth)
operator fun Point2D.div(oth: Point2D): Point2D = Point2D(x / oth.x, y / oth.y)
fun Point2D.clamp(min: Number, max: Number): Point2D = Point2D(x.clamp(min, max), y.clamp(min, max))
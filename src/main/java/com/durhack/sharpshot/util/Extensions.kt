package com.durhack.sharpshot.util

import javafx.geometry.Point2D
import java.math.BigInteger

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

fun <T: Comparable<T>> T.clamp(min: T, max: T): T {
    return when {
        this < min -> min
        this > max -> max
        else -> this
    }
}

operator fun Point2D.div(oth: Point2D): Point2D = Point2D(x / oth.x, y / oth.y)
fun Point2D.clamp(min: Double, max: Double): Point2D = Point2D(x.clamp(min, max), y.clamp(min, max))
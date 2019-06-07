package com.durhack.sharpshot.util

import java.math.BigInteger

fun Char.asBigInteger() = BigInteger(toInt().toString())
fun BigInteger.asChar() = toInt().toChar()

inline fun <reified K, reified R> Map<K, *>.filterType(): Map<K, R> =
        toList().filterIsInstance<Pair<K, R>>().toMap()

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
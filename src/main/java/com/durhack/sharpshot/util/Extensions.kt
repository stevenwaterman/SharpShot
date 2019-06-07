package com.durhack.sharpshot.util

import java.math.BigInteger

fun Char.asBigInteger() = BigInteger(toInt().toString())
fun BigInteger.asChar() = toInt().toChar()

inline fun <reified K, reified R> Map<K, *>.filterType(): Map<K, R> =
        toList().filterIsInstance<Pair<K, R>>().toMap()
package com.durhack.sharpshot.util

import java.math.BigInteger

fun Char.asBigInteger() = BigInteger(toInt().toString())
fun BigInteger.asChar() = toInt().toChar()

package com.durhack.sharpshot.util

import javafx.collections.FXCollections
import java.math.BigInteger

fun Char.asBigInteger() = BigInteger(toInt().toString())
fun BigInteger.asChar() = toInt().toChar()
fun <T> Collection<T>.observable() = FXCollections.observableArrayList(this)
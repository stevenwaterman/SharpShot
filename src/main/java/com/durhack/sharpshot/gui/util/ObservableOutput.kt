package com.durhack.sharpshot.gui.util

import javafx.beans.value.ObservableValueBase
import java.math.BigInteger

class ObservableOutput {
    private val outputs = mutableListOf<BigInteger?>()
    private val outputStrings = mutableListOf<String>()
    private var currentCount = 0

    private var confirmedString = ""
    private val string: String get() = if (currentCount == 0) "" else confirmedString + outputStrings.lastOrNull()

    private var numberChanged: () -> Unit
    private var stringChanged: () -> Unit

    val numberObservable = object : ObservableValueBase<List<BigInteger?>>() {
        init {
            numberChanged = { fireValueChangedEvent() }
        }

        override fun getValue() = outputs
    }

    val stringObservable = object : ObservableValueBase<String>() {
        init {
            stringChanged = { fireValueChangedEvent() }
        }

        override fun getValue() = string
    }

    fun add(value: BigInteger?) {
        addString(value)
        addValue(value)

        numberChanged()
        stringChanged()
    }

    private fun addString(value: BigInteger?) {
        val last = outputs.lastOrNull()
        if (currentCount > 0 && value == last) {
            currentCount++
            outputStrings.removeAt(outputStrings.size - 1)
            val string = value?.toString() ?: "E"
            outputStrings.add("$currentCount x $string")
        }
        else {
            currentCount = 1
            val string = value?.toString() ?: "E"

            val lastString = outputStrings.lastOrNull()
            if (lastString != null) {
                confirmedString += (lastString + System.lineSeparator())
            }

            outputStrings.add(string)
        }
    }

    private fun addValue(value: BigInteger?) {
        outputs.add(value)
    }

    fun addAll(values: Iterable<BigInteger?>) {
        values.forEach { value ->
            addString(value)
            addValue(value)
        }
        numberChanged()
        stringChanged()
    }

    fun clear() {
        outputs.clear()
        outputStrings.clear()
        currentCount = 0
        confirmedString = ""

        numberChanged()
        stringChanged()
    }
}
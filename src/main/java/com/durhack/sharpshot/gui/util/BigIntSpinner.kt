package com.durhack.sharpshot.gui.util

import javafx.event.Event
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.control.TextFormatter
import javafx.scene.input.ContextMenuEvent
import javafx.util.StringConverter
import tornadofx.*
import java.math.BigInteger
import java.util.function.UnaryOperator

class BigIntSpinner : Fragment() {
    val value: BigInteger? get() = root.valueFactory.value

    override val root = spinner<BigInteger>(editable = true) {
        valueFactory = BigIntValueFactory()
        valueFactory.converter = BigIntStringConverter()
        editor.textFormatter = IntTextFormatter()
        editor.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume)

        editor.textProperty().onChange { text ->
            val converter = valueFactory.converter
            val newValue = converter.fromString(text)
            valueFactory.value = newValue
        }
    }
}

private class BigIntValueFactory : SpinnerValueFactory<BigInteger>() {
    override fun increment(steps: Int) {
        if (value == null) {
            value = BigInteger.ONE
        }
        else {
            value.add(BigInteger.valueOf(steps.toLong()))
        }
    }

    override fun decrement(steps: Int) {
        val captured = value
        if (captured == null) {
            value = BigInteger.ONE
        }
        else {
            val newVal = captured.subtract(BigInteger.valueOf(steps.toLong()))
            if(newVal < BigInteger.ONE){
                value = BigInteger.ONE
            }
            else{
                value = newVal
            }
        }
    }
}

private class IntegerFilter: UnaryOperator<TextFormatter.Change?> {
    private val regex = "-?([1-9][0-9]*)?".toRegex()

    override fun apply(change: TextFormatter.Change?): TextFormatter.Change? {
        change ?: return null
        return if (change.controlNewText.matches(regex)) change else null
    }
}

private class BigIntStringConverter: StringConverter<BigInteger?>(){
    override fun toString(int: BigInteger?) = int?.toString() ?: ""
    override fun fromString(string: String?) = BigInteger(string)
}

private class IntTextFormatter: TextFormatter<BigInteger?>(BigIntStringConverter(), null, IntegerFilter())
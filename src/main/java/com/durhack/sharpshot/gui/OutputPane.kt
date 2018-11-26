package com.durhack.sharpshot.gui

import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.Priority
import tornadofx.View
import tornadofx.bind
import tornadofx.textarea
import tornadofx.vgrow
import java.math.BigInteger

class OutputPane : View() {
    private val stringProp = SimpleStringProperty("Outputs:")

    override val root = textarea {
        bind(stringProp)
        maxWidth = 200.0
        isEditable = false
        vgrow = Priority.ALWAYS
        isWrapText = true
    }

    fun setOutput(ticks: Int, ints: List<BigInteger?>) {
        stringProp.set(listOf("Ticks: $ticks", "Outputs:").asSequence().plus(ints.map {
            it?.toString() ?: "None"
        }).joinToString(System.lineSeparator()))
    }
}

package com.durhack.sharpshot.gui

import javafx.scene.layout.Priority
import tornadofx.*
import java.math.BigInteger

class OutputPane : View() {
    override val root = textarea {
        maxWidth = 200.0
        isEditable = false
        vgrow = Priority.ALWAYS
        isWrapText = true
    }

    fun setOutput(ticks: Int, ints: List<BigInteger?>) {
        root.text = listOf("Ticks: $ticks", "Outputs:")
                .asSequence()
                .plus(ints.map { it?.toString() ?: "None" })
                .joinToString(System.lineSeparator())
    }
}

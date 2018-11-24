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

    fun setOutput(ints: List<BigInteger?>) {
        root.text = ints.asSequence().map { it ?: "" }
                .joinToString(System.lineSeparator(), "Outputs:${System.lineSeparator()}")
    }
}

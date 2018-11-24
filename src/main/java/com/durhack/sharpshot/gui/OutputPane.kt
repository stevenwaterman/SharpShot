package com.durhack.sharpshot.gui

import javafx.scene.layout.Priority
import tornadofx.View
import tornadofx.textarea
import tornadofx.vgrow
import java.math.BigInteger

class OutputPane : View() {
    override val root = textarea {
        maxWidth = 200.0
        isEditable = false
        vgrow = Priority.ALWAYS
        isWrapText = true
    }

    fun setOutput(ints: List<BigInteger?>) {
        root.text = ints.map { it ?: "" }.joinToString(System.lineSeparator(), "Outputs:")
    }
}

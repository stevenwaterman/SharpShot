package com.durhack.sharpshot.gui

import tornadofx.*

class OutputPane : View() {
    override val root = textarea {
        maxWidth = 100.0
        isEditable = false
        isWrapText = true
    }

    fun print(s: String) {
        root.appendText(s + "\n")
    }

    fun clearOutput() {
        root.text = "OUT:\n\n"
    }
}

package com.durhack.sharpshot.gui.util

import javafx.scene.control.TextInputDialog
import java.math.BigInteger
import java.util.*

fun getNumberInput(header: String, content: String = "", start: BigInteger = BigInteger.ZERO): Optional<String> {
    val dialog = TextInputDialog(start.toString())
    dialog.title = "New Node"
    dialog.headerText = header
    dialog.contentText = content

    return dialog.showAndWait()
}
package com.durhack.sharpshot.gui.util

import javafx.application.Platform
import javafx.scene.control.TextInputDialog
import javafx.scene.layout.*
import javafx.scene.paint.Color
import java.math.BigInteger
import java.util.*

fun getNumberInput(header: String, content: String = "", start: BigInteger = BigInteger.ZERO): Optional<String> {
    val dialog = TextInputDialog(start.toString())
    dialog.title = "New Node"
    dialog.headerText = header
    dialog.contentText = content

    return dialog.showAndWait()
}

fun border(color: Color) =
        Border(BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))

fun ui(function: () -> Unit) = Platform.runLater(function)
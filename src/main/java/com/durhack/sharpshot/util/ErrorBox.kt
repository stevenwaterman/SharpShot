package com.durhack.sharpshot.util

import javafx.scene.control.Alert

object ErrorBox {
    fun alert(title: String, header: String, content: String) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = title
        alert.headerText = header
        alert.contentText = content
        alert.showAndWait()
    }
}

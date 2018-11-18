package com.durhack.sharpshot.util;

import javafx.scene.control.Alert;

public class ErrorBox {
    public static void alert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

package com.durhack.sharpshot.util;

import com.durhack.sharpshot.gui.Grid;
import com.durhack.sharpshot.nodes.Container;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;

public class SaveLoadFiles {

    public static Container loadFromFile(Window stage, Grid grid) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                Container cont = Serialiser.loadJSON(grid, br.readLine());

                if(cont == null) {
                    loadErrorAlert();
                } else {
                    return cont;
                }
            } catch (IOException e) {
                System.err.println("Could not open file");
                e.printStackTrace();
                loadErrorAlert();
            }
        } else {
            System.err.println("Could not open file");
            loadErrorAlert();
        }

        return null;
    }

    private static void loadErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Load Info");
        alert.setHeaderText("Error loading file!");
        alert.setContentText("Your program might not have been loaded.");
        alert.showAndWait();
    }

    public static void saveToFile(Stage stage, Container container) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save to File");
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(Serialiser.getJSON(container));
                writer.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Save Info");
                alert.setHeaderText("Saved!");
                alert.setContentText("Your program has been saved.");
                alert.showAndWait();
            } catch (IOException e) {
                System.err.println("Could not write to file");
                e.printStackTrace();
                saveErrorAlert();
            }
        } else {
            System.err.println("Could not open file");
            saveErrorAlert();
        }
    }

    private static void saveErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Save Info");
        alert.setHeaderText("Error saving file!");
        alert.setContentText("Your program might not have been saved.");
        alert.showAndWait();
    }
}

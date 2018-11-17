package com.durhack.sharpshot.util;

import com.durhack.sharpshot.nodes.Container;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;

public class SaveLoadFiles {

    public static Container loadFromFile(Window stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);

        if(file != null) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                return Serialiser.loadJSON(br.readLine());
            } catch (IOException e) {
                System.err.println("Couldnt open file");
                e.printStackTrace();
            }
        } else {
            System.err.println("Couldnt open file");
        }

        return null;
    }

    public static void saveToFile(Stage stage, Container container) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save to File");
        File file = fileChooser.showSaveDialog(stage);

        if(file != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(Serialiser.getJSON(container));
                writer.close();
            } catch (IOException e) {
                System.err.println("Couldnt write to file");
                e.printStackTrace();
            }
        } else {
            System.err.println("Couldnt open file");
        }
    }
}

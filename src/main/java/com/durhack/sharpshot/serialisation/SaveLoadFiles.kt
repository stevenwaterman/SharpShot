package com.durhack.sharpshot.serialisation

import com.durhack.sharpshot.logic.Container
import javafx.scene.control.Alert
import javafx.stage.FileChooser
import javafx.stage.Stage
import javafx.stage.Window
import java.io.*

object SaveLoadFiles {

    fun loadFromFile(stage: Window): Container? {
        val fileChooser = FileChooser()
        fileChooser.title = "Open Resource File"
        val file = fileChooser.showOpenDialog(stage)

        if (file != null) {
            try {
                val reader = BufferedReader(FileReader(file))
                return Serialiser.loadJSON(reader.readLine())
            }
            catch (e: IOException) {
                System.err.println("Could not open file")
                e.printStackTrace()
                loadErrorAlert()
            }

        }
        else {
            System.err.println("Could not open file")
            loadErrorAlert()
        }

        return null
    }

    private fun loadErrorAlert() {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = "Load Info"
        alert.headerText = "Error loading file!"
        alert.contentText = "Your program might not have been loaded."
        alert.showAndWait()
    }

    fun saveToFile(stage: Stage, container: Container) {
        val fileChooser = FileChooser()
        fileChooser.title = "Save to File"
        val file = fileChooser.showSaveDialog(stage)

        if (file != null) {
            try {
                val writer = BufferedWriter(FileWriter(file))
                writer.write(Serialiser.getJSON(container))
                writer.close()

                val alert = Alert(Alert.AlertType.INFORMATION)
                alert.title = "Save Info"
                alert.headerText = "Saved!"
                alert.contentText = "Your program has been saved."
                alert.showAndWait()
            }
            catch (e: IOException) {
                System.err.println("Could not write to file")
                e.printStackTrace()
                saveErrorAlert()
            }

        }
        else {
            System.err.println("Could not open file")
            saveErrorAlert()
        }
    }

    private fun saveErrorAlert() {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = "Save Info"
        alert.headerText = "Error saving file!"
        alert.contentText = "Your program might not have been saved."
        alert.showAndWait()
    }
}

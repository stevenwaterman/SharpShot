package com.durhack.sharpshot.gui

import com.durhack.sharpshot.Container
import com.durhack.sharpshot.TICK_RATE
import com.durhack.sharpshot.nodes.INode
import com.durhack.sharpshot.util.ErrorBox
import com.durhack.sharpshot.util.SaveLoadFiles
import com.durhack.sharpshot.util.toBigInteger
import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.control.TextInputDialog
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.stage.Stage
import java.math.BigInteger
import java.util.*

class App : Application() {

    private val runButton = Button("Run")
    private val resetButton = Button("Reset")
    private val loadButton = Button("Load")
    private val saveButton = Button("Save")
    private val clearAllButton = Button("Clear All")
    private val textInput = TextField()

    private val borderPane = BorderPane()
    private val nodeCreator = NodeCreator()
    private val grid = Grid(Container(40, 25)) { this.createNode() }

    init {
        grid.completionListeners.add { runButton.isDisable = false }
    }

    private fun createNode(): INode? {
        return nodeCreator.createNode()
    }

    override fun start(primaryStage: Stage) {
        nodeCreator.prefHeightProperty().bind(grid.prefHeightProperty())

        borderPane.center = grid
        borderPane.left = nodeCreator

        val hBox = HBox(16.0, resetButton, runButton, textInput, loadButton, saveButton, clearAllButton)
        hBox.alignment = Pos.CENTER
        borderPane.bottom = hBox

        resetButton.setOnAction {
            runButton.isDisable = false
            grid.reset()
        }

        runButton.setOnAction {
            runButton.isDisable = true
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    Platform.runLater { grid.tick() }
                }
            }, 0, TICK_RATE.toLong())
            grid.timer.cancel()
            grid.timer = timer

            val numberRegex = Regex("[0-9]+")
            val inputs = textInput.text.split(" ").filter { !it.isBlank() }.map { word ->
                when {
                    word.matches(numberRegex) -> BigInteger(word)
                    word.length == 1 -> word.first().toBigInteger()
                    else -> null
                }
            }

            if (inputs.any { it == null }) {
                ErrorBox.alert("Input not Char or BigInteger",
                               "Please try again",
                               "Input takes Char and integers only with spaces bettween them!")
            }

            clearOutput()

            grid.container.start(inputs.filterNotNull())
            grid.render()
        }

        loadButton.setOnAction {
            val container = SaveLoadFiles.loadFromFile(primaryStage)
            if (container != null) {
                grid.load(container)
            }
        }

        saveButton.setOnAction { SaveLoadFiles.saveToFile(primaryStage, grid.container) }

        clearAllButton.setOnAction { grid.clearAll() }

        programOutput.maxWidth = 100.0
        programOutput.isEditable = false
        programOutput.isWrapText = true
        clearOutput()
        borderPane.right = programOutput

        val rootScene = Scene(borderPane)
        primaryStage.scene = rootScene
        primaryStage.title = "SharpShot 1.0"

        primaryStage.setOnCloseRequest { grid.timer.cancel() }

        primaryStage.show()
    }

    companion object {
        private val programOutput = TextArea()

        fun print(c: Char) {
            programOutput.appendText(c.toString())
        }

        fun print(s: String) {
            programOutput.appendText(s + "\n")
        }


        fun clearOutput() {
            programOutput.text = "OUT:\n\n"
        }
    }
}

fun getNumberInput(header: String, content: String = "", start: BigInteger = BigInteger.ZERO): Optional<String> {
    val dialog = TextInputDialog(start.toString())
    dialog.title = "New Node"
    dialog.headerText = header
    dialog.contentText = content

    return dialog.showAndWait()
}
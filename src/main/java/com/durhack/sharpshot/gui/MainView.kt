package com.durhack.sharpshot.gui

import com.durhack.sharpshot.TICK_RATE
import com.durhack.sharpshot.gui.container.Container
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.util.SaveLoadFiles
import com.durhack.sharpshot.util.asBigInteger
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*
import java.math.BigInteger
import java.util.*

class MainView : View() {
    private val controlBar: ControlBar by inject()
    private val nodeCreator: NodeCreator by inject()
    private val outputPane: OutputPane by inject()
    private val containerScrollPane = scrollpane {  }

    private val running = SimpleBooleanProperty(false)

    private var containerView: ContainerView? = null
        set(value){
            field = value
            containerScrollPane.content = value?.root
            controlBar.containerSet.set(value != null)
        }

    private fun setContainer(container: Container) {
        val newView = ContainerView(container) { nodeCreator.createNode() }
        running.bind(newView.running)
        containerView = newView
    }

    fun newContainer(width: Int, height: Int) {
        val container = Container(width, height)
        setContainer(container)
    }

    override val root = borderpane {
        left<NodeCreator>()
        center = containerScrollPane
        left = nodeCreator.root
        right = outputPane.root
        bottom = controlBar.root
    }

    init {
        controlBar.running.bind(running)
    }

    fun start() {
        val container = containerView ?: return //Exit if container is null

        val timer = Timer()
        container.timer.cancel()
        timer.schedule(object : TimerTask() {
            override fun run() {
                Platform.runLater { container.tick() }
            }
        }, 0, TICK_RATE.toLong())
        container.timer = timer //TODO allow changing tick rate

        val inputString = controlBar.input.get()
        val unknownWords = mutableListOf<String>()
        val numberRegex = Regex("[0-9]+")
        val integers = inputString.split(",").map { word ->
            when {
                word.isBlank() -> null
                word.matches(numberRegex) -> BigInteger(word)
                word.length == 1 -> word.first().asBigInteger()
                else -> {
                    unknownWords.add(word)
                    null
                }
            }
        }

        if (unknownWords.isNotEmpty()) {
            alert(type = Alert.AlertType.ERROR,
                  buttons = *arrayOf(ButtonType.OK),
                  header = "Cannot parse inputs",
                  content = "Inputs must be integers or single ASCII characters\n" +
                          "The following inputs could not be understood\n" +
                          unknownWords.joinToString()
                 )
        }
        else{
            outputPane.clearOutput()
            container.start(integers)
            container.render()
        }
    }

    fun reset() {
        containerView?.reset()
    }

    fun loadContainer() {
        val container = SaveLoadFiles.loadFromFile(primaryStage)
        if (container != null) {
            setContainer(container)
        }
    }

    fun saveContainer() {
        val containerGui = containerView ?: return
        SaveLoadFiles.saveToFile(primaryStage, containerGui.container)
    }

    fun clear() {
        containerView?.clearAll()
    }
}
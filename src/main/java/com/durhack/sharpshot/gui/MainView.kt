package com.durhack.sharpshot.gui

import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.logic.Container
import com.durhack.sharpshot.serialisation.ContainerSaveLoad
import com.durhack.sharpshot.util.asBigInteger
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*
import java.math.BigInteger


class MainView : View() {
    private val controlBar: ControlBar by inject()
    private val nodeCreator: NodeCreator by inject()
    private val outputPane: OutputPane by inject()
    private val containerScrollPane = CenteredScrollPane()

    private val running = SimpleBooleanProperty(false)

    private var containerView: ContainerView? = null
        set(value) {
            field = value

            containerScrollPane.setContent(value?.root)
            controlBar.containerSet.set(value != null)
        }

    private fun setContainer(container: Container) {
        val newView = ContainerView(container, controlBar.tickRateProp) { nodeCreator.createNode() }
        running.bind(newView.running)
        containerView = newView
    }

    fun newContainer(width: Int, height: Int) {
        val container = Container(width, height)
        setContainer(container)
    }

    override val root = borderpane {
        left<NodeCreator>()
        center = containerScrollPane.root
        left = nodeCreator.root
        right = outputPane.root
        bottom = controlBar.root
    }

    init {
        controlBar.running.bind(running)
    }

    fun start() {
        val container = containerView ?: return //Exit if container is null

        val integers = parseInputs()
        if (integers != null) {
            outputPane.clearOutput()
            container.start(integers)
            container.animate()
        }
    }

    fun reset() {
        containerView?.reset()
    }

    fun loadContainer() {
        val container = ContainerSaveLoad.load() ?: return
        setContainer(container)
    }

    fun saveContainer() {
        val container = containerView?.container ?: return
        ContainerSaveLoad.save(container)
    }

    fun clear() {
        containerView?.clearAll()
    }

    private fun parseInputs(): List<BigInteger?>? {
        val inputString = controlBar.input.get()
        val unknownWords = mutableListOf<String>()
        val numberRegex = Regex("[0-9]+")
        val integers = inputString.split(",")
                .map { word -> word.trim() }
                .map { word ->
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

        if (unknownWords.isEmpty()) {
            return integers
        }

        alert(type = Alert.AlertType.ERROR,
              buttons = *arrayOf(ButtonType.OK),
              header = "Cannot parse inputs",
              content = "Inputs must be integers or single ASCII characters\n" +
                      "The following inputs could not be understood\n" +
                      unknownWords.joinToString()
             )
        return null

    }
}
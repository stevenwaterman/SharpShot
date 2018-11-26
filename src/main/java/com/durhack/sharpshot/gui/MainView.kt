package com.durhack.sharpshot.gui

import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.util.ui
import com.durhack.sharpshot.logic.Container
import com.durhack.sharpshot.serialisation.ContainerSaveLoad
import com.durhack.sharpshot.util.asBigInteger
import javafx.beans.InvalidationListener
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.View
import tornadofx.action
import tornadofx.alert
import tornadofx.borderpane
import tornadofx.button
import tornadofx.enableWhen
import tornadofx.hbox
import tornadofx.paddingAll
import tornadofx.vbox
import java.math.BigInteger

class MainView : View("Sharpshot") {
    private val controlBar: ControlBar by inject()
    private val nodeCreator: NodeCreator by inject()
    private val outputPane: OutputPane by inject()
    private val containerScrollPane = CenteredScrollPane()

    private val running = SimpleBooleanProperty(false)

    private lateinit var updateOutput: InvalidationListener

    init {
        controlBar.running.bind(running)
    }

    private var containerView: ContainerView? = null
        set(value) {
            field = value

            containerScrollPane.setContent(value?.root)
            controlBar.containerSet.set(value != null)
        }

    private fun setContainer(container: Container) {
        val newView = ContainerView(container, controlBar.tickRateProp, nodeCreator::createNode)
        running.bind(newView.running)

        val outputs = newView.container.outputs
        val ticks = newView.container.ticks

        updateOutput = InvalidationListener {
            if (!newView.skipping) {
                outputPane.setOutput(ticks.get(), outputs)
            }
        }

        outputs.addListener(updateOutput)
        ticks.addListener(updateOutput)

        containerView = newView
    }

    fun newContainer(width: Int, height: Int) {
        val container = Container(width, height)
        setContainer(container)
    }

    override val root = borderpane {
        center = borderpane {
            paddingAll = 8
            center = containerScrollPane.root

            left = vbox(16, Pos.CENTER) {
                enableWhen(controlBar.containerSet.and(running.not()))
                button("+") {
                    action {
                        containerView?.addColumnLeft()
                    }
                }
                button("-") {
                    action {
                        containerView?.removeColumnLeft()
                    }
                }
            }

            right = vbox(16, Pos.CENTER) {
                enableWhen(controlBar.containerSet.and(running.not()))
                button("+") {
                    action {
                        containerView?.addColumnRight()
                    }
                }
                button("-") {
                    action {
                        containerView?.removeColumnRight()
                    }
                }
            }

            top = hbox(16, Pos.CENTER) {
                enableWhen(controlBar.containerSet.and(running.not()))
                button("+") {
                    action {
                        containerView?.addRowTop()
                    }
                }
                button("-") {
                    action {
                        containerView?.removeRowTop()
                    }
                }
            }

            bottom = hbox(16, Pos.CENTER) {
                enableWhen(controlBar.containerSet.and(running.not()))
                button("+") {
                    action {
                        containerView?.addRowBottom()
                    }
                }
                button("-") {
                    action {
                        containerView?.removeRowBottom()
                    }
                }
            }
        }
        left = nodeCreator.root
        right = vbox(16, Pos.CENTER) {
            paddingAll = 16
            add(outputPane.root)
            add(controlBar.root)
        }
    }

    fun start() {
        val container = containerView ?: return //Exit if container is null

        val integers = parseInputs()
        if (integers != null) {
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
        val numberRegex = Regex("[-0-9]+")
        val integers = inputString.split(",").asSequence().map(String::trim).map { word ->
            when {
                word.isBlank()            -> null
                word.matches(numberRegex) -> BigInteger(word)
                word.length == 1          -> word.first().asBigInteger()
                else                      -> {
                    unknownWords.add(word)
                    null
                }
            }
        }.toList()

        if (unknownWords.isEmpty()) {
            return integers
        }

        alert(
                type = Alert.AlertType.ERROR,
                buttons = *arrayOf(ButtonType.OK),
                header = "Cannot parse inputs",
                content = listOf(
                        "Inputs must be integers or single ASCII characters",
                        "The following inputs could not be understood",
                        unknownWords.joinToString()).joinToString(System.lineSeparator()))
        return null
    }

    fun skipToEnd() {
        val containerView = containerView ?: return
        containerView.skipToEnd()
        ui {
            outputPane.setOutput(containerView.container.ticks.get(), containerView.container.outputs)
        }
    }
}
package com.durhack.sharpshot.gui

import com.durhack.sharpshot.core.state.Container
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.ContainerController
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.decreaseSize
import com.durhack.sharpshot.gui.container.increaseSize
import com.durhack.sharpshot.serialisation.ContainerSaveLoad
import com.durhack.sharpshot.util.asBigInteger
import javafx.beans.InvalidationListener
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*
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

    private val container: Container = Container(15, 15)
    private val containerView: ContainerView = ContainerView(container)
    val controller = ContainerController(containerView)

    override val root = borderpane {
        center = borderpane {
            paddingAll = 8
            center = containerScrollPane.root

            left = vbox(16, Pos.CENTER) {
                enableWhen(controlBar.containerSet.and(running.not()))
                button("+") {
                    action {
                        container.increaseSize(Direction.LEFT)
                    }
                }
                button("-") {
                    action {
                        container.decreaseSize(Direction.RIGHT)
                    }
                }
            }

            right = vbox(16, Pos.CENTER) {
                enableWhen(controlBar.containerSet.and(running.not()))
                button("+") {
                    action {
                        container.increaseSize(Direction.RIGHT)
                    }
                }
                button("-") {
                    action {
                        container.decreaseSize(Direction.LEFT)
                    }
                }
            }

            top = hbox(16, Pos.CENTER) {
                enableWhen(controlBar.containerSet.and(running.not()))
                button("+") {
                    action {
                        container.increaseSize(Direction.UP)
                    }
                }
                button("-") {
                    action {
                        container.decreaseSize(Direction.DOWN)
                    }
                }
            }

            bottom = hbox(16, Pos.CENTER) {
                enableWhen(controlBar.containerSet.and(running.not()))
                button("+") {
                    action {
                        container.increaseSize(Direction.DOWN)
                    }
                }
                button("-") {
                    action {
                        container.decreaseSize(Direction.UP)
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
        val integers = parseInputs() ?: return
        controller.start(integers)
    }

    fun loadContainer() {
        val loaded = ContainerSaveLoad.load() ?: return
        controller.clear()
        container.setTo(loaded)
    }

    fun saveContainer() {
        ContainerSaveLoad.save(container)
    }

    private fun parseInputs(): List<BigInteger?>? {
        val inputString = controlBar.input.get()
        val unknownWords = mutableListOf<String>()
        val numberRegex = Regex("[-0-9]+")
        val integers = inputString.split(",").asSequence().map(String::trim).map { word ->
            when {
                word.isBlank() -> null
                word.matches(numberRegex) -> BigInteger(word)
                word.length == 1 -> word.first().asBigInteger()
                else -> {
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
}
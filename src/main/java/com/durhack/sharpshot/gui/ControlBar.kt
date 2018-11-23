package com.durhack.sharpshot.gui

import com.durhack.sharpshot.DEFAULT_TICK_RATE
import com.durhack.sharpshot.gui.container.ContainerSizeDialog
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.geometry.Pos
import tornadofx.*

class ControlBar : View("Control Bar") {
    private val mainView: MainView by inject()
    private val containerSizeDialog: ContainerSizeDialog by inject()

    val input = SimpleStringProperty("")
    val running = SimpleBooleanProperty(false)
    val containerSet = SimpleBooleanProperty(false)

    val tickRateProp = SimpleLongProperty(DEFAULT_TICK_RATE)
    private val internalTickRateProp = SimpleLongProperty(DEFAULT_TICK_RATE)

    override val root = hbox(16, Pos.CENTER) {
        button("New") {
            enableWhen(running.not())
            action {
                val (width, height) = containerSizeDialog.getInput() ?: return@action
                mainView.newContainer(width, height)
            }
        }

        button("Run") {
            enableWhen(running.not().and(containerSet))
            action {
                mainView.start()
            }
        }

        button("Reset") {
            enableWhen(running.and(containerSet))
            action {
                mainView.reset()
            }
        }

        textfield {
            enableWhen(running.not().and(containerSet))
            promptText = "comma separated input"
            bind(input)
        }

        button("Load") {
            enableWhen(running.not())
            action {
                mainView.loadContainer()
            }
        }

        button("Save") {
            enableWhen(running.not().and(containerSet))
            action {
                mainView.saveContainer()
            }
        }

        button("Clear All") {
            enableWhen(running.not().and(containerSet))
            action {
                mainView.clear()
            }
        }

        label("Delay")

        textfield(internalTickRateProp) {
            prefWidth = 50.0
            isDisable = true
        }

        slider(range = 5..1000, orientation = Orientation.HORIZONTAL) {
            enableWhen(containerSet)
            bind(internalTickRateProp)
            setOnMouseReleased {
                tickRateProp.set(internalTickRateProp.get())
            }
        }
    }
}
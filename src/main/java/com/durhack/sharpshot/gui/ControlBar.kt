package com.durhack.sharpshot.gui

import com.durhack.sharpshot.gui.container.ContainerSizeDialog
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.geometry.Pos
import tornadofx.View
import tornadofx.action
import tornadofx.bind
import tornadofx.button
import tornadofx.enableWhen
import tornadofx.hbox
import tornadofx.label
import tornadofx.slider
import tornadofx.textfield
import tornadofx.vbox

@Suppress("ConvertLambdaToReference")
class ControlBar : View("Control Bar") {
    private val mainView: MainView by lazy { find(MainView::class) }
    private val containerSizeDialog: ContainerSizeDialog by inject()

    val input = SimpleStringProperty("")
    val running = SimpleBooleanProperty(false)
    val containerSet = SimpleBooleanProperty(false)

    private val internalSpeedProp = SimpleIntegerProperty(5)
    private val delays = listOf(1000, 800, 600, 450, 350, 250, 150, 75, 25, 5)
    val tickRateProp = SimpleLongProperty(delays[internalSpeedProp.get() - 1].toLong())

    override val root = vbox(16, Pos.CENTER) {
        hbox(4, Pos.CENTER) {
            button("New") {
                enableWhen(running.not())
                action {
                    val (width, height) = containerSizeDialog.getInput() ?: return@action
                    mainView.newContainer(width, height)
                }
            }

            button("Clear All") {
                enableWhen(running.not().and(containerSet))
                action { mainView.clear() }
            }
        }

        textfield {
            enableWhen(running.not().and(containerSet))
            promptText = "comma separated input"
            bind(input)
        }

        hbox(4, Pos.CENTER) {
            button("Run") {
                enableWhen(running.not().and(containerSet))
                action { mainView.start() }
            }

            button("Reset") {
                enableWhen(running.and(containerSet))
                action { mainView.reset() }
            }
        }

        hbox(4, Pos.CENTER) {
            button("Load") {
                enableWhen(running.not())
                action { mainView.loadContainer() }
            }

            button("Save") {
                enableWhen(running.not().and(containerSet))
                action { mainView.saveContainer() }
            }
        }

        vbox(4, Pos.CENTER) {
            hbox(16, Pos.CENTER) {
                label("Speed")

                textfield(internalSpeedProp) {
                    prefWidth = 50.0
                    isDisable = true
                }
            }

            slider(range = 1..10, orientation = Orientation.HORIZONTAL) {
                isShowTickMarks = true
                isSnapToTicks = true
                majorTickUnit = 1.0
                minorTickCount = 0
                bind(internalSpeedProp)
                setOnMouseReleased {
                    tickRateProp.set(delays[internalSpeedProp.get() - 1].toLong())
                }
            }
        }
    }
}
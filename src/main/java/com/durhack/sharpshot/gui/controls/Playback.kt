package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.gui.container.ContainerController
import com.durhack.sharpshot.gui.util.not
import com.durhack.sharpshot.util.container
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.Event
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.input.ContextMenuEvent
import tornadofx.*

class Playback : View() {
    private val controller: ContainerController by inject()

    private val runningLayer = hbox(8.0, Pos.CENTER) {
        visibleWhen(container.runningProp)

        val animateProp = SimpleBooleanProperty(true)
        val speedSettingProp = SimpleIntegerProperty(2)
        label("Speed") {
            visibleWhen(animateProp)
        }
        slider(1, 5, orientation = Orientation.HORIZONTAL) {
            isFocusTraversable = false
            blockIncrement = 1.0
            majorTickUnit = 1.0
            isSnapToTicks = true
            isShowTickMarks = true
            minorTickCount = 0
            visibleWhen(animateProp)
            enableWhen(controller.idleProp)
            bind(speedSettingProp)
        }
        checkbox("Animate", animateProp) {
            isFocusTraversable = false
            enableWhen(controller.idleProp)
        }
        button("Tick") {
            isFocusTraversable = false
            enableWhen(controller.idleProp)
            action {

                if (animateProp.get()) {
                    val speedSetting = speedSettingProp.get()
                    val lengthMs = getPlaybackMs(speedSetting)
                    controller.animatedTick(lengthMs)
                }
                else {
                    controller.quickTick()
                }

            }
        }
        button("Reset") {
            isFocusTraversable = false
            enableWhen(controller.idleProp)
            action {

                controller.reset()

            }
        }
        stackpane {
            button("Play") {
                isFocusTraversable = false
                hiddenWhen(controller.playingProp)
                enableWhen(animateProp.booleanBinding(controller.idleProp) { animateProp.get() && controller.idle })
                action {
                    val speedSetting = speedSettingProp.get()
                    val lengthMs = getPlaybackMs(speedSetting)
                    controller.play(lengthMs)
                }
            }
            button("Stop") {
                isFocusTraversable = false
                visibleWhen(controller.playingProp)
                enableWhen(controller.stoppingProp.not())
                action {
                    controller.stopPlaying()
                }
            }
        }
        button("Fast-Forward") {
            isFocusTraversable = false
            enableWhen(controller.idleProp)
            action {
                controller.simulate(10 * 1000)
            }
        }
    }

    private val stoppedLayer = hbox(8.0, Pos.CENTER) {
        alignment = Pos.CENTER
        visibleWhen(container.runningProp.not())

        val inputsProp = SimpleStringProperty("")
        textfield(inputsProp) {
            promptText = "Space-separated inputs"
            addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume)
            val digits = "0123456789"
            filterInput { change ->
                val oldInputs = inputsProp.get()
                return@filterInput when {
                    change.text == "-" -> oldInputs.isBlank() || oldInputs.last() == ' '
                    change.text == " " -> oldInputs.isNotEmpty() && oldInputs.last() in digits
                    else -> change.text.all { it in digits }
                }
            }
        }
        button("Start Test") {
            isFocusTraversable = false
            action {
                val inputString = inputsProp.get()
                val inputs = inputString.split(" ").filter { it.isNotBlank() }.map { it.toBigInteger() }
                controller.start(inputs)
            }
        }
    }

    override val root = stackpane {
        add(runningLayer)
        add(stoppedLayer)
    }

    private fun getPlaybackMs(speedSetting: Int): Long {
        return when (speedSetting) {
            1 -> 2000L
            2 -> 500L
            3 -> 250L
            4 -> 50L
            5 -> 10L
            else -> throw IllegalArgumentException("Invalid speed setting")
        }
    }
}

package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.gui.container.ContainerController
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import tornadofx.*

class Playback : View() {
    private val controller: ContainerController by inject()

    override val root =
            hbox(16.0, Pos.CENTER) {
                button {
                    text = "Tick"
                    action { controller.quickTick() }
                    isFocusTraversable = false
                }
                button {
                    text = "Animated Tick"
                    action { controller.animatedTick(1000) }
                    isFocusTraversable = false
                }
                button {
                    text = "Simulate"
                    action { controller.simulate(1e6.toInt()) }
                    isFocusTraversable = false
                }
                button {
                    text = "Reset"
                    action { controller.reset() }
                    isFocusTraversable = false
                }
                button {
                    text = "Clear"
                    action { controller.clear() }
                    isFocusTraversable = false
                }
                val inputProp = SimpleStringProperty("")
                textfield {
                    bind(inputProp)
                    promptText = "Comma-separated inputs"
                }
                button {
                    text = "Start"
                    action { controller.start(inputProp.get().split(" ").map { it.toBigInteger() }) }
                    isFocusTraversable = false
                }
            }
}
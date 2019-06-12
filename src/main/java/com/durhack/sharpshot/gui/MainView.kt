package com.durhack.sharpshot.gui

import com.durhack.sharpshot.gui.container.CenteredScrollPane
import com.durhack.sharpshot.gui.container.ContainerController
import com.durhack.sharpshot.gui.container.menus.ContainerInputLayer
import javafx.beans.property.SimpleStringProperty
import javafx.event.Event
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.input.KeyEvent
import tornadofx.*

class MainView: View(){
    private val controller: ContainerController by inject()
    private val scrollPane: CenteredScrollPane by inject()
    private val inputLayer: ContainerInputLayer by inject()

    override val root = borderpane {
        center{
            add(scrollPane)
        }

        bottom {
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
                    action{controller.start(inputProp.get().split(" ").map { it.toBigInteger() })}
                    isFocusTraversable = false
                }
            }
        }

        addEventFilter(KeyEvent.KEY_PRESSED) {
            if (it.target == scrollPane.root) {
                redirect(it)
            }
        }
    }

    private fun redirect(event: KeyEvent){
        event.consume()
        val newEvent = event.withTarget(inputLayer.root)
        Event.fireEvent(inputLayer.root, newEvent)
    }
}

fun KeyEvent.withTarget(target: EventTarget): KeyEvent =
        KeyEvent(source, target, eventType, character, text, code, isShiftDown, isControlDown, isAltDown, isMetaDown)
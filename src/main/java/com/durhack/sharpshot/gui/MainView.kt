package com.durhack.sharpshot.gui

import com.durhack.sharpshot.gui.container.CenteredScrollPane
import com.durhack.sharpshot.gui.container.menus.ContainerInputLayer
import javafx.event.Event
import javafx.event.EventTarget
import javafx.scene.input.KeyEvent
import tornadofx.*

class MainView: View(){
    private val scrollPane: CenteredScrollPane by inject()
    private val inputLayer: ContainerInputLayer by inject()

    override val root = borderpane {
        center{
            add(scrollPane)

            addEventFilter(KeyEvent.KEY_PRESSED) {
                if (it.target == scrollPane.root) {
                    redirect(it)
                }
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
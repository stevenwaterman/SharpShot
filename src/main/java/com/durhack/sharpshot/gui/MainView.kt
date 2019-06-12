package com.durhack.sharpshot.gui

import com.durhack.sharpshot.gui.container.CenteredScrollPane
import com.durhack.sharpshot.gui.container.menus.ContainerInputLayer
import com.durhack.sharpshot.gui.controls.Output
import com.durhack.sharpshot.gui.controls.Playback
import com.durhack.sharpshot.gui.controls.SizeModifier
import javafx.event.Event
import javafx.event.EventTarget
import javafx.geometry.Orientation
import javafx.scene.input.KeyEvent
import tornadofx.*

class MainView: View(){
    private val scrollPane: CenteredScrollPane by inject()
    private val inputLayer: ContainerInputLayer by inject()
    private val playback: Playback by inject()
    private val output: Output by inject()
    private val size: SizeModifier by inject()

    override val root = borderpane {
        center { add(scrollPane) }
        bottom { add(playback) }
        right {
            vbox(16.0) {
                add(output)
                separator(Orientation.HORIZONTAL)
                add(size)
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
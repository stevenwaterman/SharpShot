package com.durhack.sharpshot.gui

import com.durhack.sharpshot.gui.container.input.layers.ContainerInputLayer
import com.durhack.sharpshot.gui.controls.*
import javafx.event.Event
import javafx.event.EventTarget
import javafx.scene.input.KeyEvent
import tornadofx.*

class MainView : View() {
    private val scrollZoomPane: ScrollZoomPane by inject()
    private val inputLayer: ContainerInputLayer by inject()
    private val playback: Playback by inject()
    private val output: Output by inject()
    private val info: ContainerInfo by inject()
    private val saveLoadMenu: SaveLoadMenu by inject()
    private val extract: ExtractInfo by inject()
    private val selectionInfo: SelectionInfo by inject()

    override val root = borderpane {
        id = "Main View"

        center { add(scrollZoomPane) }
        bottom { add(playback) }
        left {
            paddingAll = 12.0
            vbox(4.0) {
                paddingAll = 4.0
                add(selectionInfo)
                separator()
                add(extract)
            }
        }
        right {
            vbox(4.0) {
                paddingAll = 4.0
                add(output)
                separator()
                add(info)
                separator()
                add(saveLoadMenu)
            }
        }

        addEventFilter(KeyEvent.KEY_PRESSED) {
            if (it.target == scrollZoomPane.root) {
                redirect(it)
            }
        }
    }

    private fun redirect(event: KeyEvent) {
        event.consume()
        val newEvent = event.withTarget(inputLayer.root)
        Event.fireEvent(inputLayer.root, newEvent)
    }
}

fun KeyEvent.withTarget(target: EventTarget): KeyEvent =
        KeyEvent(source, target, eventType, character, text, code, isShiftDown, isControlDown, isAltDown, isMetaDown)
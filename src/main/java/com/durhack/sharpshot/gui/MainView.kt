package com.durhack.sharpshot.gui

import com.durhack.sharpshot.gui.container.CenteredScrollPane
import javafx.scene.input.KeyEvent
import tornadofx.*

class MainView: View(){
    private val scrollPane: CenteredScrollPane by inject()

    override val root = borderpane {
        center{
            add(scrollPane)

            addEventFilter(KeyEvent.KEY_PRESSED){
                it.target //TODO retarget event to input layer if its targetting the scroll pane (consume then fire)
                println("${it.target} ${it.code}")
            }
        }
    }
}
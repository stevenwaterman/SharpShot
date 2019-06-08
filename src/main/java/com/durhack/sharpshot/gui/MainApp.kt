package com.durhack.sharpshot.gui

import javafx.stage.Stage
import tornadofx.*

class MainApp: App(MainView::class){
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 1280.0
        stage.height = 720.0
        stage.centerOnScreen()
    }
}
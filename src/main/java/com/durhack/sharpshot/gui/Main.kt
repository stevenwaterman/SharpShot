package com.durhack.sharpshot.gui

import javafx.stage.Stage
import tornadofx.App
import tornadofx.launch

class Program : App(MainView::class){
    override fun start(stage: Stage) {
        super.start(stage)
        stage.isMaximized = true
    }
}

fun main(args: Array<String>) {
    launch<Program>()
}
package com.durhack.sharpshot

import com.durhack.sharpshot.gui.App
import javafx.application.Application

internal object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Application.launch(App::class.java, *args)
    }
}

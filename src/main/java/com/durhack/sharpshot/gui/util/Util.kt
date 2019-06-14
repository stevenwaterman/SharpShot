package com.durhack.sharpshot.gui.util

import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.input.MouseEvent

fun ui(function: () -> Unit) = Platform.runLater(function)

fun Node.addClickHandler(func: (MouseEvent) -> Unit){
    addEventHandler(MouseEvent.MOUSE_CLICKED){event ->
        if (event.isStillSincePress){
            func(event)
        }
    }
}

fun Node.addClickFilter(func: (MouseEvent) -> Unit){
    addEventFilter(MouseEvent.MOUSE_CLICKED){event ->
        if (event.isStillSincePress){
            func(event)
        }
    }
}
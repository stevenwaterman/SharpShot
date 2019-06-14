package com.durhack.sharpshot.gui.util

import javafx.beans.property.StringProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.layout.Background
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import tornadofx.*

class ReadOnlyTextArea(property: ObservableValue<String>): Fragment(){
    private val inner = vbox {
        alignment = Pos.CENTER
        vgrow = Priority.ALWAYS
        label {
            bind(property)
            alignment = Pos.CENTER
            vgrow = Priority.ALWAYS
        }
    }

    override val root = scrollpane(true, false) {
        vgrow = Priority.ALWAYS
        background = Background.EMPTY
        isPannable = true

        vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER

        add(inner)

        inner.heightProperty().onChange { ui{vvalue = vmax} }
    }
}
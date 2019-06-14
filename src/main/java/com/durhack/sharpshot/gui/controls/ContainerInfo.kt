package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.util.container
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.text.Font
import tornadofx.*

class ContainerInfo() : View() {
    private val containerWidth = container.widthProp
    private val containerHeight = container.heightProp

    override val root = vbox(8.0) {
        alignment = Pos.CENTER
        label("Info") {
            font = Font(18.0)
        }
        hbox {
            alignment = Pos.CENTER
            label("Size: ")
            label {
                bind(containerWidth)
            }
            label(" x ")
            label {
                bind(containerHeight)
            }
        }
    }
}
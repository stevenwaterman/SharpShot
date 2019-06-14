package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.gui.container.ContainerController
import com.durhack.sharpshot.util.container
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.text.Font
import tornadofx.*

class ContainerInfo() : View() {
    private val containerWidth = container.widthProp
    private val containerHeight = container.heightProp
    private val controller: ContainerController by inject()

    override val root = vbox(8.0, Pos.CENTER) {
        label("Info") {
            font = Font(18.0)
        }
        hbox(alignment = Pos.CENTER) {
            label("Size: ")
            label {
                bind(containerWidth)
            }
            label(" x ")
            label {
                bind(containerHeight)
            }
        }
        hbox(alignment = Pos.CENTER) {
            label("Ticks: ")
            label {
                bind(controller.tickProp)
            }
        }
    }
}
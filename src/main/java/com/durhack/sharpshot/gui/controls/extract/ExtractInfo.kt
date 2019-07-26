package com.durhack.sharpshot.gui.controls.extract

import com.durhack.sharpshot.util.globalExtract
import com.durhack.sharpshot.util.globalExtractProp
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.text.Font
import tornadofx.*

class ExtractInfo : View() {
    private val extractSet = globalExtractProp.booleanBinding(globalExtractProp) { it != null }
    private val extractWidth = globalExtractProp.integerBinding(globalExtractProp) { it?.width ?: 0 }
    private val extractHeight = globalExtractProp.integerBinding(globalExtractProp) { it?.height ?: 0 }

    private val extractPreview: SmallExtractPreview by inject()

    private val transformContext = contextmenu {
        item("Rotate").action {
            globalExtract = globalExtract?.rotatedClockwise ?: return@action
        }

        item("Flip Horiz").action {
            globalExtract = globalExtract?.mirroredHorizontal ?: return@action
        }

        item("Flip Vert").action {
            globalExtract = globalExtract?.mirroredVertical ?: return@action
        }

        item("Trim").action {
            globalExtract = globalExtract?.trimmed ?: return@action
        }
    }

    override val root = vbox(8.0, Pos.CENTER) {
        id = "Extract Info"

        label("Extract") {
            font = Font(18.0)
        }

        add(extractPreview)

        hbox(alignment = Pos.CENTER) {
            enableWhen(extractSet)
            label("Size: ")
            label {
                bind(extractWidth)
            }
            label(" x ")
            label {
                bind(extractHeight)
            }
        }

        button("Place") {
            enableWhen(extractSet)
            action {
                //TODO
            }
        }

        button("Transform") {
            enableWhen(extractSet)

            contextMenu = transformContext

            val anchor = this
            action {
                contextMenu.show(anchor, Side.BOTTOM, 0.0, 0.0)
            }
        }

        hbox {
            button("Load") {
                enableWhen(extractSet)
                action {
                    //TODO
                }
            }

            button("Save") {
                enableWhen(extractSet)
                action {
                    //TODO
                }
            }
        }
    }


}
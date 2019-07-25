package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.gui.container.ContainerStaticRenderer
import com.durhack.sharpshot.gui.container.Extract
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class ExtractInfo : View() {
    var extract: Extract? = null
        set(extract) {
            if (extract == null) {
                extractSet.set(false)
                renderer.clear()
            }
            else {
                extractSet.set(true)
                extractWidth.set(extract.width)
                extractHeight.set(extract.height)
                renderer.renderExtract(extract, renderScale)
            }

            field = extract
        }


    private val extractSet = SimpleBooleanProperty(false)
    private val extractWidth = SimpleIntegerProperty(0)
    private val extractHeight = SimpleIntegerProperty(0)

    private val renderScale = 8
    private val renderer = ContainerStaticRenderer()

    override val root = vbox(8.0, Pos.CENTER) {
        id = "Extract Info"

        label("Extract") {
            font = Font(18.0)
        }

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

        add(renderer)

        button("Place") {
            enableWhen(extractSet)
            action {
                //TODO
            }
        }

        button("Rotate") {
            enableWhen(extractSet)
            action {
                val capt = extract
                if (capt != null) {
                    capt.rotateClockwise()
                    extractWidth.set(capt.width)
                    extractHeight.set(capt.height)
                    renderer.renderExtract(capt, renderScale)
                }
            }
        }

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
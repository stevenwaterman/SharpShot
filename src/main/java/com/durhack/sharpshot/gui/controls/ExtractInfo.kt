package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.gui.ExtractPreview
import com.durhack.sharpshot.gui.container.ContainerStaticRenderer
import com.durhack.sharpshot.gui.container.Extract
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*
import kotlin.math.min

class ExtractInfo : View() {
    var extract: Extract? = null
        set(extract) {
            field = extract
            if (extract == null || extract.isEmpty) {
                extractSet.set(false)
                renderer.clear()
            }
            else {
                extractSet.set(true)
                extractWidth.set(extract.width)
                extractHeight.set(extract.height)
                render()
            }
        }


    private val extractSet = SimpleBooleanProperty(false)
    private val extractWidth = SimpleIntegerProperty(0)
    private val extractHeight = SimpleIntegerProperty(0)

    private val extractPreview: ExtractPreview by inject()

    private val renderSize = 60.0
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

        stackpane {
            id = "Small Extract Preview"
            prefWidth = renderSize
            prefHeight = renderSize

            setOnMouseEntered {
                extractPreview.show()
            }
            setOnMouseExited {
                extractPreview.hide()
            }

            add(renderer)
        }

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
                    render()
                }
            }
        }

        button("Flip Horiz") {
            enableWhen(extractSet)
            action {
                val capt = extract
                if (capt != null) {
                    capt.mirrorHorizontal()
                    extractWidth.set(capt.width)
                    extractHeight.set(capt.height)
                    render()
                }
            }
        }

        button("Flip Vert") {
            enableWhen(extractSet)
            action {
                val capt = extract
                if (capt != null) {
                    capt.mirrorVertical()
                    extractWidth.set(capt.width)
                    extractHeight.set(capt.height)
                    render()
                }
            }
        }

        button("Trim") {
            enableWhen(extractSet)
            action {
                val capt = extract
                if (capt != null) {
                    capt.trim()
                    extractWidth.set(capt.width)
                    extractHeight.set(capt.height)
                    render()
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

    private fun render() {
        val capt = extract ?: return
        val requiredWidth = renderSize / capt.width
        val requiredHeight = renderSize / capt.height
        val scale = min(requiredWidth, requiredHeight).toInt()
        renderer.renderExtract(capt, scale)
    }
}
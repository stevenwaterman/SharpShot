package com.durhack.sharpshot.gui.controls.extract

import com.durhack.sharpshot.gui.container.ContainerStaticRenderer
import com.durhack.sharpshot.util.globalExtract
import com.durhack.sharpshot.util.globalExtractProp
import javafx.geometry.Insets
import javafx.scene.input.MouseButton
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.math.min

class SmallExtractPreview : View() {
    private val largeExtractPreview: LargeExtractPreview by inject()
    private val renderSize = 60.0
    private val renderer = ContainerStaticRenderer()

    init {
        globalExtractProp.onChange { render() }
    }

    override val root = stackpane {
        id = "Small extract Preview"
        prefWidth = renderSize
        prefHeight = renderSize
        background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))

        setOnMousePressed {
            if (it.button == MouseButton.PRIMARY) {
                largeExtractPreview.show()
            }
        }
        setOnMouseReleased {
            if (it.button == MouseButton.PRIMARY) {
                largeExtractPreview.hide()
            }
        }

        add(renderer)
    }

    private fun render() {
        val capt = globalExtract ?: return
        val requiredWidth = renderSize / capt.width
        val requiredHeight = renderSize / capt.height
        val scale = min(requiredWidth, requiredHeight).toInt()
        renderer.renderExtract(scale)
    }
}
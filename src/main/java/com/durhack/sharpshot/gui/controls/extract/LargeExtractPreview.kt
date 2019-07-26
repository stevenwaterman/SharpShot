package com.durhack.sharpshot.gui.controls.extract

import com.durhack.sharpshot.gui.container.ContainerStaticRenderer
import com.durhack.sharpshot.gui.container.ContainerView
import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*

class LargeExtractPreview : View() {
    private val renderer = ContainerStaticRenderer()

    override val root = stackpane {
        id = "Large extract Preview"
        background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))

        add(renderer)
        isVisible = false
    }

    fun show() {
        renderer.renderExtract(ContainerView.scale)
        root.isVisible = true
    }

    fun hide() {
        root.isVisible = false
    }
}
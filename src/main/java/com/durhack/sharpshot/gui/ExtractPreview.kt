package com.durhack.sharpshot.gui

import com.durhack.sharpshot.gui.container.ContainerStaticRenderer
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.Extract
import com.durhack.sharpshot.gui.controls.ExtractInfo
import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*

class ExtractPreview : View() {
    private val renderer = ContainerStaticRenderer()
    private val extractInfo: ExtractInfo by inject()
    private val extract: Extract? get() = extractInfo.extract

    override val root = stackpane {
        id = "Large Extract Preview"
        background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))

        add(renderer)
        isVisible = false

    }

    fun show() {
        val capt = extract ?: return
        renderer.renderExtract(capt, ContainerView.scale)
        root.isVisible = true
    }

    fun hide() {
        root.isVisible = false
    }
}
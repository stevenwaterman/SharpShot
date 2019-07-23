package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.gui.container.ContainerController
import com.durhack.sharpshot.gui.util.ReadOnlyTextArea
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import tornadofx.*


class Output : View() {
    private val controller: ContainerController by inject()
    private val text = ReadOnlyTextArea(controller.outputStringProp)

    override val root = vbox(spacing = 12.0) {
        prefWidth = 100.0
        alignment = Pos.TOP_CENTER
        vgrow = Priority.ALWAYS

        label {
            text = "Out"
            alignment = Pos.TOP_CENTER
            textAlignment = TextAlignment.CENTER
            font = Font(18.0)
        }
        add(text)
    }
}
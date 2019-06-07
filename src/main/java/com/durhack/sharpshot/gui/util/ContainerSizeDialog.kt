package com.durhack.sharpshot.gui.util

import com.durhack.sharpshot.util.DEFAULT_CONTAINER_SIZE
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class ContainerSizeDialog : Dialog<Pair<Int, Int>>("Container Size") {
    private val gridWidthProp = SimpleIntegerProperty(DEFAULT_CONTAINER_SIZE.x)
    private val gridHeightProp = SimpleIntegerProperty(DEFAULT_CONTAINER_SIZE.y)

    override fun getValue(): Pair<Int, Int>? {
        val width = gridWidthProp.get()
        val height = gridHeightProp.get()
        return width to height
    }

    override fun getDialogContents() = form {
        fieldset("Container Size") {
            field("Width") {
                textfield(gridWidthProp) {
                    promptText = "Enter grid width in squares"
                }
            }
            field("Height") {
                textfield(gridHeightProp) {
                    promptText = "Enter grid height in squares"
                }
            }
        }
    }
}
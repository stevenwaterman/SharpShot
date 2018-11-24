package com.durhack.sharpshot.gui.util

import javafx.geometry.Pos
import tornadofx.Form
import tornadofx.View
import tornadofx.action
import tornadofx.button
import tornadofx.hbox
import tornadofx.stackpane

abstract class Dialog<T>(title: String) : View(title) {
    protected abstract fun getValue(): T?
    private var submitted = false

    abstract fun getDialogContents(): Form

    override val root = stackpane { }

    fun getInput(): T? {
        root.children.clear()

        getDialogContents().run {
            hbox(16, Pos.CENTER) {
                button("Ok") {
                    action {
                        submitted = true
                        close()
                    }
                }
                button("Cancel") {
                    action(this@Dialog::close)
                }
            }
        }

        submitted = false
        openModal(block = true, resizable = false)
        return when {
            submitted -> getValue()
            else -> null
        }
    }
}
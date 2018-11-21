package com.durhack.sharpshot.gui.util

import javafx.geometry.Pos
import javafx.scene.Node
import tornadofx.*

abstract class Dialog<T>(title: String) : View(title) {
    protected abstract fun getValue(): T?
    private var submitted = false

    abstract fun getDialogContents(): Node
    private val dialogContentsHolder = stackpane { }

    override val root = vbox(16) {
        add(dialogContentsHolder)
        hbox(16, Pos.CENTER) {
            button("Ok") {
                action {
                    submitted = true
                    close()
                }
            }
            button("Cancel") {
                action {
                    close()
                }
            }
        }
    }

    fun getInput(): T? {
        dialogContentsHolder.children.clear()
        dialogContentsHolder.children.add(getDialogContents())

        submitted = false
        openModal(block = true, resizable = false)
        return when {
            submitted -> getValue()
            else -> null
        }
    }
}
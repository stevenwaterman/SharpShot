package com.durhack.sharpshot.gui

import com.durhack.sharpshot.core.state.Container
import javafx.beans.InvalidationListener
import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.Priority
import tornadofx.*

class OutputPane : View() {
    private val stringProp = SimpleStringProperty("Outputs:")
    var container: Container? = null
        set(container) {
            field = container
            container ?: return

            //container.ticks.addListener(invalidationListener)
            //container.outputs.addListener(invalidationListener)
            updateOutput()
        }

    /**
     * Cheap synchronisation because we don't care about missed updates
     */
    private var updating: Boolean = false

    private val invalidationListener = InvalidationListener {
        if (!updating) {
            updating = true
            updateOutput()
        }
    }

    override val root = textarea {
        bind(stringProp)
        maxWidth = 200.0
        isEditable = false
        vgrow = Priority.ALWAYS
        isWrapText = true
    }

    fun updateOutput() {
        val container = container
        /*
        ui {
            if (container == null) {
                stringProp.set("")
            }
            else {
                val string = emptySequence<String>()
                        .plus("Ticks: ${container.ticks.get()}")
                        .plus("Outputs:")
                        .plus(container.outputs.map { it?.toString() ?: "None" })
                        .joinToString(System.lineSeparator())

                stringProp.set(string)
            }

            updating = false
        }
        */
        //TODO
    }
}
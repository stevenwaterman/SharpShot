package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.gui.container.ContainerController
import javafx.beans.InvalidationListener
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import tornadofx.*

class Output : View() {
    private val controller: ContainerController by inject()
    private val outputObservable = OutputStringObservable(controller)

    override val root = vbox(spacing = 8.0){
        prefWidth = 100.0
        alignment = Pos.TOP_CENTER
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS

        label {
            text = "Out"
            alignment = Pos.TOP_CENTER
            font = Font(18.0)
        }
        spacer {
            prefHeight = 20.0
        }
        scrollpane {
            isFitToHeight = true
            isFitToWidth = true
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
            label {
                bind(outputObservable)
                alignment = Pos.TOP_CENTER
                isFocusTraversable = false
            }
        }
    }
}

class OutputStringObservable(private val containerController: ContainerController) : ObservableValue<String> {
    private val changeListeners: MutableList<ChangeListener<in String>> = mutableListOf()
    private var lastValue: String = ""

    init {
        containerController.outputs.addListener(InvalidationListener {
            changeListeners.forEach { it.changed(this@OutputStringObservable, lastValue, value) }
            lastValue = value
        })
    }

    override fun removeListener(listener: ChangeListener<in String>?) {
        listener ?: return
        changeListeners.remove(listener)
    }

    override fun removeListener(listener: InvalidationListener?) {
        containerController.outputs.removeListener(listener)
    }

    override fun addListener(listener: ChangeListener<in String>?) {
        listener ?: return
        changeListeners.add(listener)
    }

    override fun addListener(listener: InvalidationListener?) {
        containerController.outputs.addListener(listener)
    }

    override fun getValue() = containerController.outputs.joinToString(System.lineSeparator())
}
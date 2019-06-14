package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.gui.container.ContainerController
import com.durhack.sharpshot.gui.util.ReadOnlyTextArea
import javafx.beans.InvalidationListener
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import tornadofx.*


class Output : View() {
    private val controller: ContainerController by inject()
    private val outputObservable = OutputStringObservable(controller)
    private val text = ReadOnlyTextArea(outputObservable)

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
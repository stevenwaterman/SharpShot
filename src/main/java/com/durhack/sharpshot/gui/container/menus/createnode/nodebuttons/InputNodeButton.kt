package com.durhack.sharpshot.gui.container.menus.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.input.InputNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.util.BigIntSpinner
import com.durhack.sharpshot.registry.NodeRegistry
import com.durhack.sharpshot.util.clamp
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.text.Font
import tornadofx.*

class InputNodeButton(
        onHover: (AbstractNodeButton) -> Unit,
        showForm: (AbstractNodeForm) -> Unit,
        nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeButton(NodeRegistry.inputNodeEntry, onHover, showForm, nodeCreated) {

    override val nodeForm = InputNodeForm(nodeCreated)
    override fun createNode() = null
}

class InputNodeForm(nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeForm(nodeCreated) {

    private val spinner = BigIntSpinner()

    override fun focus() {
        spinner.root.requestFocus()
    }

    override fun onHide() {
        spinner.value = null
    }

    override val root = vbox {
        alignment = Pos.CENTER
        paddingAll = 16.0
        spacing = 4.0

        addEventFilter(KeyEvent.KEY_PRESSED) { event ->
            when {
                event.code == KeyCode.Q -> {
                    event.consume()
                    acceptNormal()
                }
                event.code == KeyCode.W -> {
                    event.consume()
                    acceptEmpty()
                }
            }
        }

        label("Which input should this node output?") {
            font = Font(18.0)
        }
        hbox(4.0) {
            alignment = Pos.CENTER

            add(spinner)

            button("Accept") {
                action { acceptNormal() }
            }
            button("Empty") {
                action { acceptEmpty() }
            }
        }
    }

    private fun acceptNormal() {
        val value = spinner.value?.toInt()?.clamp(min = 0)
        val node = InputNode(value, Direction.UP)
        accept(node)
    }

    private fun acceptEmpty() {
        val node = InputNode(null, Direction.UP)
        accept(node)
    }

    private fun accept(node: AbstractNode) {
        nodeCreated(node)
    }
}
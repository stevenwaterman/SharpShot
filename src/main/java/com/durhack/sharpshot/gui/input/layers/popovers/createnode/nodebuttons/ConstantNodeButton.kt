package com.durhack.sharpshot.gui.input.layers.popovers.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.other.ConstantNode
import com.durhack.sharpshot.core.nodes.other.RandomNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.util.BigIntSpinner
import com.durhack.sharpshot.registry.NodeRegistry
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.text.Font
import tornadofx.*

class ConstantNodeButton(
        onHover: (AbstractNodeButton) -> Unit,
        showForm: (AbstractNodeForm) -> Unit,
        nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeButton(NodeRegistry.constantNodeEntry, onHover, showForm,
                           nodeCreated) {

    override val nodeForm = ConstantNodeForm(nodeCreated)
    override fun createNode() = null
}

class ConstantNodeForm(nodeCreated: (AbstractNode) -> Unit) :
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
                event.code == KeyCode.E -> {
                    event.consume()
                    acceptRandom()
                }
            }
        }

        label("What value should this node output?") {
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
            button("Random") {
                action { acceptRandom() }
            }
        }
    }

    private fun acceptNormal() {
        val value = spinner.value
        val node = ConstantNode(value, Direction.UP)
        accept(node)
    }

    private fun acceptEmpty() {
        val node = ConstantNode(null, Direction.UP)
        accept(node)
    }

    private fun acceptRandom() {
        val node = RandomNode(Direction.UP)
        accept(node)
    }

    private fun accept(node: AbstractNode) {
        nodeCreated(node)
    }
}
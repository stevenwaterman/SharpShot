package com.durhack.sharpshot.gui.input.layers.popovers.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.memory.MemoryNode
import com.durhack.sharpshot.core.nodes.memory.StackNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

class MemoryNodeButton(
        onHover: (AbstractNodeButton) -> Unit,
        showForm: (AbstractNodeForm) -> Unit,
        nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeButton("Memory Node", "Stores values", graphicCreator, onHover, showForm, nodeCreated) {

    companion object {
        private val graphicCreator: (Int) -> Canvas = { scale ->
            Canvas(scale.toDouble(), scale.toDouble()).apply {
                Draw.triangle(graphicsContext2D, Direction.UP, 0.0, 0.0, scale, Color.CRIMSON)
            }
        }
    }

    override val nodeForm = MemoryNodeForm(nodeCreated)
    override fun createNode() = null
}

class MemoryNodeForm(nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeForm(nodeCreated) {

    override fun focus() {}
    override fun onHide() {}

    override val root = vbox {
        alignment = Pos.CENTER
        paddingAll = 16.0
        spacing = 4.0

        addEventFilter(KeyEvent.KEY_PRESSED) { event ->
            when {
                event.code == KeyCode.Q -> {
                    event.consume()
                    acceptMemory()
                }
                event.code == KeyCode.W -> {
                    event.consume()
                    acceptStack()
                }
            }
        }

        label("Select Memory Type") {
            font = Font(18.0)
        }
        hbox(4.0) {
            alignment = Pos.CENTER

            button("Single").apply {
                action { acceptMemory() }
            }
            button("Stack") {
                action { acceptStack() }
            }
        }
    }

    private fun acceptMemory() {
        val node = MemoryNode(Direction.UP)
        accept(node)
    }

    private fun acceptStack() {
        val node = StackNode(Direction.UP)
        accept(node)
    }

    private fun accept(node: AbstractNode) {
        nodeCreated(node)
    }
}
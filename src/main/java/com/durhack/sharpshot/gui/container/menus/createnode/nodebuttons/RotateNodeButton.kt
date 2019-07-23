package com.durhack.sharpshot.gui.container.menus.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.routing.RotateAntiNode
import com.durhack.sharpshot.core.nodes.routing.RotateNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.NodeRegistry
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.text.Font
import tornadofx.*

class RotateNodeButton(
        onHover: (AbstractNodeButton) -> Unit,
        showForm: (AbstractNodeForm) -> Unit,
        nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeButton("Rotation Node", "Changes bullet direction dependent on incoming direction", graphicCreator, onHover, showForm, nodeCreated) {

    companion object {
        private val graphicCreator: (Int) -> Canvas = { scale ->
            NodeRegistry.rotateNodeEntry.getGraphic(scale)
        }
    }

    override val nodeForm = RotateNodeForm(nodeCreated)
    override fun createNode() = null
}

class RotateNodeForm(nodeCreated: (AbstractNode) -> Unit) :
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
                    acceptClockwise()
                }
                event.code == KeyCode.W -> {
                    event.consume()
                    acceptAnti()
                }
            }
        }

        label("Select rotation direction") {
            font = Font(18.0)
        }
        hbox(4.0) {
            alignment = Pos.CENTER

            button("Clockwise").apply {
                action { acceptClockwise() }
            }
            button("Anticlockwise"){
                action { acceptAnti() }
            }
        }
    }

    private fun acceptClockwise() {
        val node = RotateNode(Direction.UP)
        accept(node)
    }

    private fun acceptAnti() {
        val node = RotateAntiNode(Direction.UP)
        accept(node)
    }

    private fun accept(node: AbstractNode) {
        nodeCreated(node)
    }
}
package com.durhack.sharpshot.gui.container.input.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.routing.conditional.IfNullNode
import com.durhack.sharpshot.core.nodes.routing.conditional.IfPositiveNode
import com.durhack.sharpshot.core.nodes.routing.conditional.IfZeroNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

class ConditionalNodeButton(
        onHover: (AbstractNodeButton) -> Unit,
        showForm: (AbstractNodeForm) -> Unit,
        nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeButton(
                "Conditional Node",
                "Redirects bullets when certain conditions are met",
                graphicCreator,
                onHover,
                showForm,
                nodeCreated
                          ) {

    companion object {
        private val graphicCreator: (Int) -> Canvas = { scale ->
            Canvas(scale.toDouble(), scale.toDouble()).apply {
                Draw.triangle(graphicsContext2D, Direction.UP, 0.0, 0.0, scale, Color.LIGHTSKYBLUE)
            }
        }
    }

    override val nodeForm = ConditionalNodeForm(nodeCreated)
    override fun createNode() = null
}

class ConditionalNodeForm(nodeCreated: (AbstractNode) -> Unit) :
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
                    acceptZero()
                }
                event.code == KeyCode.W -> {
                    event.consume()
                    acceptPositive()
                }
                event.code == KeyCode.E -> {
                    event.consume()
                    acceptEmpty()
                }
            }
        }

        label("When should bullets be redirected?") {
            font = Font(18.0)
        }
        hbox(4.0) {
            alignment = Pos.CENTER

            button("Equal to Zero") {
                action { acceptZero() }
            }
            button("Over Zero") {
                action { acceptPositive() }
            }
            button("Empty") {
                action { acceptEmpty() }
            }
        }
    }

    private fun acceptZero() {
        val node = IfZeroNode(Direction.UP)
        accept(node)
    }

    private fun acceptPositive() {
        val node = IfPositiveNode(Direction.UP)
        accept(node)
    }

    private fun acceptEmpty() {
        val node = IfNullNode(Direction.UP)
        accept(node)
    }

    private fun accept(node: AbstractNode) {
        nodeCreated(node)
    }
}
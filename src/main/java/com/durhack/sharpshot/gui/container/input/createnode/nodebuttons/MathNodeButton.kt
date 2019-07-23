package com.durhack.sharpshot.gui.container.input.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.math.AddNode
import com.durhack.sharpshot.core.nodes.math.DivNode
import com.durhack.sharpshot.core.nodes.math.MultNode
import com.durhack.sharpshot.core.nodes.math.SubNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

class MathNodeButton(
        onHover: (AbstractNodeButton) -> Unit,
        showForm: (AbstractNodeForm) -> Unit,
        nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeButton(
                "Math Node",
                "Performs basic arithmetic",
                graphicCreator,
                onHover,
                showForm,
                nodeCreated
                          ) {

    companion object {
        private val graphicCreator: (Int) -> Canvas = { scale ->
            Canvas(scale.toDouble(), scale.toDouble()).apply {
                Draw.triangle(graphicsContext2D, Direction.UP, 0.0, 0.0, scale, Color.PLUM)
            }
        }
    }

    override val nodeForm = MathNodeForm(nodeCreated)
    override fun createNode() = null
}

class MathNodeForm(nodeCreated: (AbstractNode) -> Unit) :
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
                    acceptPlus()
                }
                event.code == KeyCode.W -> {
                    event.consume()
                    acceptMinus()
                }
                event.code == KeyCode.E -> {
                    event.consume()
                    acceptTimes()
                }
                event.code == KeyCode.R -> {
                    event.consume()
                    acceptDivide()
                }
            }
        }

        label("Select Function") {
            font = Font(18.0)
        }
        hbox(4.0) {
            alignment = Pos.CENTER

            button("Add").apply {
                action { acceptPlus() }
            }
            button("Subtract") {
                action { acceptMinus() }
            }
            button("Multiply") {
                action { acceptTimes() }
            }
            button("Divide") {
                action { acceptDivide() }
            }
        }
    }

    private fun acceptPlus() {
        val node = AddNode(Direction.UP)
        accept(node)
    }

    private fun acceptMinus() {
        val node = SubNode(Direction.UP)
        accept(node)
    }

    private fun acceptTimes() {
        val node = MultNode(Direction.UP)
        accept(node)
    }

    private fun acceptDivide() {
        val node = DivNode(Direction.UP)
        accept(node)
    }

    private fun accept(node: AbstractNode) {
        nodeCreated(node)
    }
}
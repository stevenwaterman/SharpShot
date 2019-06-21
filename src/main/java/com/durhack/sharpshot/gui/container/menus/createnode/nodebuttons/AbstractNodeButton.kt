package com.durhack.sharpshot.gui.container.menus.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.gui.util.addClickHandler
import com.durhack.sharpshot.registry.RegistryEntry
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.*

abstract class AbstractNodeButton(
        val name: String,
        val description: String,
        val graphicCreator: (Int) -> Node,
        private val onHover: (AbstractNodeButton) -> Unit,
        private val showForm: (AbstractNodeForm) -> Unit,
        private val nodeCreated: (AbstractNode) -> Unit) :
        Fragment() {

    constructor(registryEntry: RegistryEntry<*>,
                onHover: (AbstractNodeButton) -> Unit,
                showForm: (AbstractNodeForm) -> Unit,
                nodeCreated: (AbstractNode) -> Unit) :
            this(registryEntry.name,
                 registryEntry.description,
                 {registryEntry.getGraphic(it)},
                 onHover,
                 showForm,
                 nodeCreated)

    companion object {
        const val scale = 28
    }

    abstract val nodeForm: AbstractNodeForm?
    abstract fun createNode(): AbstractNode?

    override val root = stackpane {
        add(graphicCreator(scale))
        add(ShadeOnHover())

        addEventHandler(MouseEvent.MOUSE_ENTERED) { _ -> onHover(this@AbstractNodeButton) }

        addClickHandler { event ->
            if (event.button == MouseButton.PRIMARY) {
                event.consume()
                clicked()
            }
        }
    }

    fun clicked() {
        val form = nodeForm
        if (form == null) {
            val node = createNode()
            if (node != null) {
                nodeCreated(node)
            }
        }
        else {
            showForm(form)
        }
    }
}

private class ShadeOnHover : Pane() {
    companion object {
        private val hoverColor = Color.color(0.1, 0.1, 0.2, 0.2)
        private val hoverBackground = Background(BackgroundFill(hoverColor, CornerRadii.EMPTY, Insets.EMPTY))
    }

    init {
        addEventHandler(MouseEvent.MOUSE_ENTERED) { _ -> background = hoverBackground }
        addEventHandler(MouseEvent.MOUSE_EXITED) { _ -> background = Background.EMPTY }
    }
}

abstract class AbstractNodeForm(protected val nodeCreated: (AbstractNode) -> Unit) : Fragment() {
    abstract fun focus()
    abstract fun onHide()
}
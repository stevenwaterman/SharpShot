package com.durhack.sharpshot.gui.container.menus

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.menus.nodecreator.NodeCreator
import com.durhack.sharpshot.gui.container.menus.nodecreator.nodeforms.AbstractNodeForm
import com.durhack.sharpshot.registry.RegistryEntry
import com.durhack.sharpshot.util.clamp
import com.durhack.sharpshot.util.container
import javafx.scene.input.MouseButton
import tornadofx.*

class ClickLayer : View() {
    private val containerView: ContainerView by inject()
    private val nodeCreator = NodeCreator { nodeSelected(it) }
    private val nodeFormPane = pane {
        layoutXProperty().bind(nodeCreator.root.layoutXProperty())
        layoutYProperty().bind(nodeCreator.root.layoutYProperty())
        prefWidthProperty().bind(nodeCreator.root.prefWidthProperty())
        prefHeightProperty().bind(nodeCreator.root.prefHeightProperty())
    }

    private lateinit var coord: Coordinate
    private var creatorOpen = false
    private var formOpen = false
    private val eitherOpen get() = creatorOpen || formOpen

    override val root = pane {
        setOnMousePressed {
            if (!eitherOpen && it.button == MouseButton.PRIMARY) {
                val scale = ContainerView.scaleProp.get()
                val xClicked = (it.x / scale).toInt().clamp(0, container.width - 1)
                val yClicked = (it.y / scale).toInt().clamp(0, container.height - 1)
                coord = Coordinate(xClicked, yClicked)

                if(container.nodes[coord] == null){
                    showNodeCreator()
                }
            }
        }

        add(nodeCreator)
        add(nodeFormPane)
    }
    //TODO stop it going off the screen when zoomed in and have it so the left hand side is centered rather than the whole thing

    init {
        hideNodeCreator()
    }

    private fun nodeSelected(entry: RegistryEntry<out AbstractNode>?) {
        hideNodeCreator()
        entry ?: return

        val form = entry.getNodeForm(
                close = { hideNodeForm() },
                success = { node ->
                    container.nodes[coord] = node
                    containerView.render()
                })

        if (form == null) {
            val node = entry.createNode()
            container.nodes[coord] = node
            containerView.render()
        }
        else {
            showForm(form)
        }
    }

    private fun showNodeCreator() {
        creatorOpen = true

        val x = (0.5 + coord.x) * containerView.scale
        val width = nodeCreator.root.width
        val topLeftX = x - (width / 2)
        val maxX = containerView.width - width
        val clampedX = topLeftX.clamp(0, maxX)

        val y = (0.5 + coord.y) * containerView.scale
        val height = nodeCreator.root.height
        val topLeftY = y - (height / 2)
        val maxY = containerView.height - height
        val clampedY = topLeftY.clamp(0, maxY)

        nodeCreator.root.layoutX = clampedX
        nodeCreator.root.layoutY = clampedY
        nodeCreator.root.isVisible = true
    }

    private fun hideNodeCreator() {
        creatorOpen = false
        nodeCreator.root.isVisible = false
    }

    private fun showForm(form: AbstractNodeForm<*>){
        formOpen = true
        nodeFormPane.add(form)
    }

    private fun hideNodeForm(){
        formOpen = false
        nodeFormPane.clear()
    }

    fun hideAll() {
        hideNodeCreator()
        hideNodeForm()
    }
}

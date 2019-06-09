package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.menus.nodecreator.NodeCreator
import com.durhack.sharpshot.registry.RegistryEntry
import com.durhack.sharpshot.util.clamp
import com.durhack.sharpshot.util.container
import javafx.scene.input.MouseButton
import tornadofx.*

class ClickLayer() : View() {
    private val containerView: ContainerView by inject()
    private val nodeCreator = NodeCreator { onSelection(it) }

    private lateinit var coord: Coordinate
    private var open = false

    override val root = pane {
        setOnMousePressed {
            if (!open && it.button == MouseButton.PRIMARY) {
                val scale = ContainerView.scaleProp.get()
                val xClicked = (it.x / scale).toInt().clamp(0, container.width - 1)
                val yClicked = (it.y / scale).toInt().clamp(0, container.height - 1)
                coord = Coordinate(xClicked, yClicked)

                if(container.nodes[coord] == null){
                    showCreator()
                }
            }
        }

        add(nodeCreator)
    }

    init {
        hide()
    }

    private fun onSelection(entry: RegistryEntry<out AbstractNode>?) {
        hide()

        entry ?: return

        val node = entry.guiCreate()
        container.nodes[coord] = node
        containerView.render()
    }

    private fun showCreator() {
        open = true

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

    fun hide() {
        open = false
        nodeCreator.root.isVisible = false
    }
}

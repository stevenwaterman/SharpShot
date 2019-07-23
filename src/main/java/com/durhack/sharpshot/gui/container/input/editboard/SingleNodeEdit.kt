package com.durhack.sharpshot.gui.container.input.editboard

import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.input.ContainerInputLayer
import com.durhack.sharpshot.gui.util.coord
import com.durhack.sharpshot.util.container
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Priority
import tornadofx.*

class SingleNodeEdit : View() {
    private val containerView: ContainerView by inject()
    private val inputLayer: ContainerInputLayer by inject()

    override val root = pane {
        id = "Single Node Edit"
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS
    }

    fun handleRotate(event: MouseEvent) {
        val coord = event.coord ?: return
        val node = container.nodes[coord] ?: return
        event.consume()
        node.clockwise()
        containerView.render()
    }

    fun handleDelete(event: MouseEvent) {
        val coord = event.coord ?: return
        event.consume()
        delete(coord)
    }

    fun rotateHoveredCW() {
        val coord = inputLayer.hoverCoord ?: return
        val node = container.nodes[coord] ?: return
        node.clockwise()
        containerView.render()
    }

    fun rotateHoveredACW() {
        val coord = inputLayer.hoverCoord ?: return
        val node = container.nodes[coord] ?: return
        node.anticlockwise()
        containerView.render()
    }

    fun deleteHovered() {
        delete(inputLayer.hoverCoord ?: return)
    }

    private fun delete(coord: Coordinate) {
        container.nodes.remove(coord)
        containerView.render()
    }
}

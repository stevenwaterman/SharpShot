package com.durhack.sharpshot.gui.container.menus

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.menus.createnode.CreateNodeMenu
import com.durhack.sharpshot.util.clamp
import com.durhack.sharpshot.util.container
import javafx.geometry.Point2D
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import tornadofx.*

class InputLayer : View() {
    private val containerView: ContainerView by inject()
    private val nodeCreator = CreateNodeMenu { addNode(it) }

    private lateinit var coord: Coordinate

    override val root = pane {
        add(nodeCreator)
        addEventHandler(MouseEvent.MOUSE_PRESSED) { if (it.button == MouseButton.PRIMARY) clicked(it.x, it.y) }
    }

    private fun clicked(x: Double, y: Double) {
        val scale = ContainerView.scaleProp.get()
        val xClicked = (x / scale).toInt().clamp(0, container.width - 1)
        val yClicked = (y / scale).toInt().clamp(0, container.height - 1)

        val newCoord = Coordinate(xClicked, yClicked)
        if (newCoord.exists){
            coord = newCoord
            if (container.nodes[coord] == null) nodeCreator.show(Point2D(x, y))
        }
    }

    private fun addNode(node: AbstractNode?) {
        node ?: return
        container.nodes[coord] = node
        containerView.render()
    }
}

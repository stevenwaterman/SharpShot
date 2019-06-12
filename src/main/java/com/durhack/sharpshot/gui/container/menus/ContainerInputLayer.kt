package com.durhack.sharpshot.gui.container.menus

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.menus.createnode.CreateNodeMenu
import com.durhack.sharpshot.gui.util.addClickHandler
import com.durhack.sharpshot.util.clamp
import com.durhack.sharpshot.util.container
import javafx.geometry.Point2D
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import tornadofx.*

class ContainerInputLayer : View() {
    private val containerView: ContainerView by inject()
    private val nodeCreator = CreateNodeMenu { coord, node -> addNode(coord, node) }

    private var mouseX: Double = 0.0
    private var mouseY: Double = 0.0
    private val mouseCoord: Coordinate? get() = getCoord(mouseX, mouseY)

    override val root = pane {
        add(nodeCreator)

        addClickHandler {
            if (it.button == MouseButton.PRIMARY){
                clicked(it.x, it.y)
            }
            else if(it.button == MouseButton.SECONDARY){
                deleteClicked(it.x, it.y)
            }
            it.consume()
        }

        addEventHandler(KeyEvent.KEY_PRESSED){
            when {
                it.code == KeyCode.Q -> rotateHoveredACW()
                it.code == KeyCode.E -> rotateHoveredCW()
                it.code in listOf(KeyCode.DELETE, KeyCode.BACK_SPACE, KeyCode.R) -> deleteHovered()
            }
        }

        addEventHandler(MouseEvent.MOUSE_PRESSED){
            if(it.button != MouseButton.SECONDARY){
                it.consume()
            }
        }

        addEventHandler(MouseEvent.MOUSE_DRAGGED){
            if(it.button != MouseButton.SECONDARY){
                it.consume()
            }
        }

        setOnMouseMoved {
            mouseX = it.x
            mouseY = it.y
        }
    }

    private fun getCoord(x: Double, y: Double): Coordinate? {
        val scale = ContainerView.scaleProp.get()
        val xClicked = (x / scale).toInt().clamp(0, container.width - 1)
        val yClicked = (y / scale).toInt().clamp(0, container.height - 1)

        val coord = Coordinate(xClicked, yClicked)
        if(coord.exists){
            return coord
        }
        else{
            return null
        }
    }

    private fun rotateHoveredCW() {
        val coord = mouseCoord ?: return
        val node = container.nodes[coord] ?: return
        node.direction = node.direction.clockwise
        containerView.render()
    }

    private fun rotateHoveredACW() {
        val coord = mouseCoord ?: return
        val node = container.nodes[coord] ?: return
        node.direction = node.direction.antiClockwise
        containerView.render()
    }

    private fun deleteHovered() {
        val coord = mouseCoord ?: return
        container.nodes.remove(coord)
        containerView.render()
    }

    private fun deleteClicked(x: Double, y: Double) {
        val coord = getCoord(x, y) ?: return
        container.nodes.remove(coord)
        containerView.render()
    }

    private fun clicked(x: Double, y: Double) {
        val coord = getCoord(x, y) ?: return
        val node = container.nodes[coord]
        if (node == null) {
            nodeCreator.show(coord, Point2D(x, y))
        }
        else{
            node.direction = node.direction.clockwise
            containerView.render()
        }
    }

    private fun addNode(coordinate: Coordinate, node: AbstractNode?) {
        node ?: return
        container.nodes[coordinate] = node
        containerView.render()
    }
}
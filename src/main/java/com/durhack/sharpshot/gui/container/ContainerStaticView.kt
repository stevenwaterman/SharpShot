package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.core.state.Container
import com.durhack.sharpshot.registry.NodeRegistry
import com.durhack.sharpshot.util.container
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import tornadofx.*

class ContainerStaticView() : View() {
    private val renderer = ContainerStaticRenderer()

    override val root = pane {
        add(renderer)
    }

    fun render(scale: Double){
        renderer.clear()
        renderer.drawNodes(container, scale)
        renderer.drawGrid(container, scale)
    }
}

class ContainerStaticRenderer() : Canvas() {
    val gc = graphicsContext2D

    fun clear() {
        gc.clearRect(0.0, 0.0, width, height)
    }

    fun drawNodes(container: Container, scale: Double){
        container.nodes.forEach { (coord, node) ->
            val x = coord.x * scale
            val y = coord.y * scale
            NodeRegistry.draw(node, gc, x, y, scale)
        }
    }

    fun drawGrid(container: Container, scale: Double) {
        gc.stroke = Color.GRAY
        gc.lineWidth = 2.0

        width = scale * container.width
        height = scale * container.height

        (0..container.width).map { it * scale }.forEach {//TODO disable zooming when mouse button is down not just when dragging
            gc.strokeLine(it, 0.0, it, height)
        }

        (0..container.height).map { it * scale }.forEach {
            gc.strokeLine(0.0, it, width, it)
        }
    }
}
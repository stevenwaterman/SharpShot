package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.registry.NodeRegistry
import com.durhack.sharpshot.util.container
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color

class ContainerStaticRenderer : Canvas() {
    private val gc = graphicsContext2D

    fun renderContainer(scale: Int) = render(scale, container.width, container.height, container.nodes)
    fun renderExtract(extract: Extract, scale: Int) = render(scale, extract.width, extract.height, extract.nodes)

    fun render(scale: Int, width: Int, height: Int, nodes: Map<Coordinate, AbstractNode>) {
        clear()
        updateSize(scale, width, height)
        drawNodes(scale, nodes)
        drawGrid(scale, width, height)
    }

    private fun updateSize(scale: Int, width: Int, height: Int) {
        this.width = scale * width + 0.5
        this.height = scale * height + 0.5
    }

    fun clear() {
        gc.clearRect(0.0, 0.0, width, height)
    }

    private fun drawNodes(scale: Int, nodes: Map<Coordinate, AbstractNode>) {
        nodes.forEach { (coord, node) ->
            val x = coord.x * scale
            val y = coord.y * scale
            NodeRegistry.draw(node, gc, x + 0.5, y + 0.5, scale)
        }
    }

    private fun drawGrid(scale: Int, width: Int, height: Int) {
        gc.stroke = Color.GRAY
        gc.lineWidth = 1.0

        (0..width).map { it * scale }.forEach {
            gc.strokeLine(it + 0.5, 0.0, it + 0.5, this.height)
        }

        (0..height).map { it * scale }.forEach {
            gc.strokeLine(0.0, it + 0.5, this.width, it + 0.5)
        }
    }
}
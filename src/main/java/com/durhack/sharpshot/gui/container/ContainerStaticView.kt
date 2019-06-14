package com.durhack.sharpshot.gui.container

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

    fun render(){
        renderer.render(ContainerView.scale)
    }
}

class ContainerStaticRenderer() : Canvas() {
    private val gc = graphicsContext2D

    fun render(scale: Int){
        clear()
        updateWidth(scale)
        drawNodes(scale)
        drawGrid(scale)
    }

    private fun updateWidth(scale: Int) {
        width = scale * container.width + 0.5
        height = scale * container.height + 0.5
    }

    private fun clear() {
        gc.clearRect(0.0, 0.0, width, height)
    }

    private fun drawNodes(scale: Int){
        container.nodes.forEach { (coord, node) ->
            val x = coord.x * scale
            val y = coord.y * scale
            NodeRegistry.draw(node, gc, x + 0.5, y + 0.5, scale)
        }
    }

    private fun drawGrid(scale: Int) {
        gc.stroke = Color.GRAY
        gc.lineWidth = 1.0

        (0..container.width).map { it * scale }.forEach {//TODO disable zooming when mouse button is down not just when dragging
            gc.strokeLine(it + 0.5, 0.0, it +0.5, height)
        }

        (0..container.height).map { it * scale }.forEach {
            gc.strokeLine(0.0, it + 0.5, width, it + 0.5)
        }
    }
}
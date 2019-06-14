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
        renderer.render()
    }
}

class ContainerStaticRenderer() : Canvas() {
    private val gc = graphicsContext2D

    fun render(){
        clear()
        updateWidth()
        drawNodes()
        drawGrid()
    }

    private fun updateWidth() {
        width = ContainerView.scaleProp.get() * container.width + 0.5
        height = ContainerView.scaleProp.get() * container.height + 0.5
    }

    private fun clear() {
        gc.clearRect(0.0, 0.0, width, height)
    }

    private fun drawNodes(){
        container.nodes.forEach { (coord, node) ->
            val x = coord.x * ContainerView.scaleProp.get()
            val y = coord.y * ContainerView.scaleProp.get()
            NodeRegistry.draw(node, gc, x + 0.5, y + 0.5, ContainerView.scale)
        }
    }

    private fun drawGrid() {
        gc.stroke = Color.GRAY
        gc.lineWidth = 1.0

        (0..container.width).map { it * ContainerView.scaleProp.get() }.forEach {//TODO disable zooming when mouse button is down not just when dragging
            gc.strokeLine(it + 0.5, 0.0, it +0.5, height)
        }

        (0..container.height).map { it * ContainerView.scaleProp.get() }.forEach {
            gc.strokeLine(0.0, it + 0.5, width, it + 0.5)
        }
    }
}
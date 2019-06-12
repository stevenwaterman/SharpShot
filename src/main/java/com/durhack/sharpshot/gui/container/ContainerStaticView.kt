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
        renderer.clear()
        renderer.updateWidth()
        renderer.drawNodes()
        renderer.drawGrid()
    }
}

class ContainerStaticRenderer() : Canvas() {
    private val gc = graphicsContext2D

    fun updateWidth() {
        width = ContainerView.scaleProp.get() * container.width
        height = ContainerView.scaleProp.get() * container.height
    }

    fun clear() {
        gc.clearRect(0.0, 0.0, width, height)
    }

    fun drawNodes(){
        container.nodes.forEach { (coord, node) ->
            val x = coord.x * ContainerView.scaleProp.get()
            val y = coord.y * ContainerView.scaleProp.get()
            NodeRegistry.draw(node, gc, x, y, ContainerView.scaleProp.get())
        }
    }

    fun drawGrid() {
        gc.stroke = Color.GRAY
        gc.lineWidth = 2.0

        (0..container.width).map { it * ContainerView.scaleProp.get() }.forEach {//TODO disable zooming when mouse button is down not just when dragging
            gc.strokeLine(it, 0.0, it, height)
        }

        (0..container.height).map { it * ContainerView.scaleProp.get() }.forEach {
            gc.strokeLine(0.0, it, width, it)
        }
    }
}
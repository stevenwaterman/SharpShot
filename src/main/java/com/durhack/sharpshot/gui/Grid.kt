package com.durhack.sharpshot.gui

import com.durhack.sharpshot.Container
import com.durhack.sharpshot.Coordinate
import com.durhack.sharpshot.GRID_SIZE
import com.durhack.sharpshot.TICK_RATE
import com.durhack.sharpshot.nodes.INode
import com.durhack.sharpshot.util.Listeners
import javafx.animation.TranslateTransition
import javafx.scene.Node
import javafx.scene.input.MouseButton
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.StrokeType
import javafx.util.Duration
import java.util.*

class Grid(val container: Container, private val getUiSelectedNode: () -> INode?) : Pane() {
    val completionListeners = Listeners()

    private var running = false
    var timer = Timer()

    init {
        // Call program completion listeners when container is done
        container.completionListeners.add(completionListeners)
        resize((container.width * GRID_SIZE).toDouble(), (container.height * GRID_SIZE).toDouble())
        render()
    }

    fun render() {
        children.clear()

        for ((coordinate, node) in container.nodes) {
            val graphic = node.graphic()
            graphic.relocate((coordinate.x * GRID_SIZE).toDouble(), (coordinate.y * GRID_SIZE).toDouble())
            children.add(graphic)
        }

        for ((coordinate, bullet) in container.bullets) {

            val graphic = bullet.toGraphic()

            val translateTransition = TranslateTransition(Duration.millis(TICK_RATE.toDouble()))
            translateTransition.node = graphic

            var prevPos = Coordinate(coordinate.x, coordinate.y)
            prevPos = Coordinate(prevPos.x - bullet.direction.deltaX, prevPos.y - bullet.direction.deltaY)
            graphic.relocate((prevPos.x * GRID_SIZE).toDouble(), (prevPos.y * GRID_SIZE).toDouble())

            translateTransition.toX = (bullet.direction.deltaX * GRID_SIZE).toDouble()
            translateTransition.toY = (bullet.direction.deltaY * GRID_SIZE).toDouble()

            translateTransition.isAutoReverse = false
            children.add(graphic)
            translateTransition.play()
        }

        for (x in 0..container.width) {
            for (y in 0..container.height) {
                val background = emptyGraphic(Coordinate(x, y))
                background.relocate((x * GRID_SIZE).toDouble(), (y * GRID_SIZE).toDouble())
                children.add(background)
            }
        }
    }

    fun tick() {
        running = true
        container.tick()
        render()
    }

    fun reset() {
        timer.cancel()
        container.reset()
        running = false
        render()
    }

    private fun emptyGraphic(coordinate: Coordinate): Node {
        val rectangle = Rectangle(GRID_SIZE.toDouble(), GRID_SIZE.toDouble(), Color.TRANSPARENT)
        rectangle.stroke = Color.GRAY
        rectangle.strokeWidth = 0.5
        rectangle.strokeType = StrokeType.CENTERED
        rectangle.setOnMousePressed { mouseEvent ->
            val currentNode = container.nodes[coordinate]
            if (!running) {
                if (currentNode == null) {
                    val newNode = getUiSelectedNode()
                    if (newNode != null && mouseEvent.button == MouseButton.PRIMARY) {
                        container.nodes[coordinate] = newNode
                        render()
                    }
                }
                else {
                    if (mouseEvent.button == MouseButton.PRIMARY) {
                        currentNode.rotate()
                        render()
                    }
                    else if (mouseEvent.button == MouseButton.SECONDARY) {
                        container.nodes.remove(coordinate)
                        render()
                    }
                }
            }
        }

        return rectangle
    }

    fun clearAll() {
        container.clearAll()
        App.clearOutput()
        reset()
    }

    fun load(newContainer: Container) {
        container.clearAll()
        container.nodes.putAll(newContainer.nodes)
        render()
    }
}

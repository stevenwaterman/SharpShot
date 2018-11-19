package com.durhack.sharpshot.gui

import com.durhack.sharpshot.Coordinate
import com.durhack.sharpshot.INode
import com.durhack.sharpshot.nodes.Container
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
        resize((container.width * 32).toDouble(), (container.height * 32).toDouble())
        render()
    }

    fun render() {
        children.clear()

        for ((coordinate, node) in container.nodes) {
            val graphic = node.toGraphic()
            graphic.relocate((coordinate.x * 32).toDouble(), (coordinate.y * 32).toDouble())
            children.add(graphic)
        }

        for ((coordinate, bullet) in container.bullets) {

            val graphic = bullet.toGraphic()

            val translateTransition = TranslateTransition(Duration.millis(App.TICK_RATE.toDouble()))
            translateTransition.node = graphic

            var prevPos = Coordinate(coordinate.x, coordinate.y)
            prevPos = Coordinate(prevPos.x - bullet.direction.deltaX, prevPos.y - bullet.direction.deltaY)
            graphic.relocate((prevPos.x * 32).toDouble(), (prevPos.y * 32).toDouble())

            translateTransition.toX = (bullet.direction.deltaX * 32).toDouble()
            translateTransition.toY = (bullet.direction.deltaY * 32).toDouble()

            translateTransition.isAutoReverse = false
            children.add(graphic)
            translateTransition.play()
        }

        for (x in 0..container.width) {
            for (y in 0..container.height) {
                val background = emptyGraphic(Coordinate(x, y))
                background.relocate((x * 32).toDouble(), (y * 32).toDouble())
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
        val rectangle = Rectangle(32.0, 32.0, Color.TRANSPARENT)
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
                        currentNode.rotateClockwise()
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
    }
}

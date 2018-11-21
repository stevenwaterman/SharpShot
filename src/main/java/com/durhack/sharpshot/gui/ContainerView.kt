package com.durhack.sharpshot.gui

import com.durhack.sharpshot.GRID_SIZE
import com.durhack.sharpshot.logic.Container
import com.durhack.sharpshot.logic.Coordinate
import com.durhack.sharpshot.nodes.INode
import javafx.animation.Interpolator
import javafx.animation.TranslateTransition
import javafx.beans.value.ObservableLongValue
import javafx.scene.Node
import javafx.scene.input.MouseButton
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.StrokeType
import javafx.util.Duration
import tornadofx.*
import java.math.BigInteger
import java.util.*
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

class ContainerView(val container: Container,
                    val tickRateProp: ObservableLongValue,
                    private val getUiSelectedNode: () -> INode?) : Fragment() {
    val running = container.running

    var animating = false
    var timer = Timer()

    override val root = pane {
        minWidth = container.width * GRID_SIZE.toDouble()
        maxWidth = minWidth
        minHeight = container.height * GRID_SIZE.toDouble()
        maxHeight = minHeight
    }

    fun animate() {
        timer.cancel()

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                tick()
            }
        }, 0, tickRateProp.get())

        animating = true
    }

    init {
        render()
        tickRateProp.onChange {
            if (animating) {
                animate()
            }
        }
    }

    fun render() {
        runAsync { } ui {
            root.children.clear()

            container.nodes.forEach { (coordinate, node) ->
                val graphic = node.graphic()
                graphic.relocate((coordinate.x * GRID_SIZE).toDouble(), (coordinate.y * GRID_SIZE).toDouble())
                root.children.add(graphic)
            }

            container.bullets.forEach { (coordinate, bullet) ->
                val graphic = bullet.toGraphic()

                val translateTransition = TranslateTransition(Duration.millis(tickRateProp.get().toDouble()))
                translateTransition.node = graphic

                var prevPos = Coordinate(coordinate.x, coordinate.y)
                prevPos = Coordinate(prevPos.x - bullet.direction.deltaX,
                                     prevPos.y - bullet.direction.deltaY)
                graphic.relocate((prevPos.x * GRID_SIZE).toDouble(), (prevPos.y * GRID_SIZE).toDouble())

                translateTransition.toX = (bullet.direction.deltaX * GRID_SIZE).toDouble()
                translateTransition.toY = (bullet.direction.deltaY * GRID_SIZE).toDouble()
                translateTransition.interpolator = Interpolator.LINEAR //TODO is this what we want?

                translateTransition.isAutoReverse = false
                root.children.add(graphic)
                translateTransition.play()
            }

            (0..(container.width - 1)).forEach { x ->
                for (y in 0..(container.height - 1)) {
                    val background = emptyGraphic(Coordinate(x, y))
                    background.relocate((x * GRID_SIZE).toDouble(), (y * GRID_SIZE).toDouble())
                    root.children.add(background)
                }
            }
        }
    }

    fun tick() {
        container.tick()
        render()
    }

    fun reset() {
        animating = false
        timer.cancel()
        container.reset()
        render()
    }

    private fun emptyGraphic(coordinate: Coordinate): Node {
        val rectangle = Rectangle(GRID_SIZE.toDouble(), GRID_SIZE.toDouble(), Color.TRANSPARENT)
        rectangle.stroke = Color.GRAY
        rectangle.strokeWidth = 0.5
        rectangle.strokeType = StrokeType.CENTERED

        rectangle.setOnMousePressed { mouseEvent ->
            if (!running.get()) {
                val currentNode = container.nodes[coordinate]
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
        reset()
    }

    fun start(inputs: List<BigInteger?>) {
        container.start(inputs)
    }
}

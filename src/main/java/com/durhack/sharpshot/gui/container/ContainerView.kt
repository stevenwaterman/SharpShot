package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.GRID_SIZE
import com.durhack.sharpshot.gui.util.ui
import com.durhack.sharpshot.logic.Container
import com.durhack.sharpshot.logic.Coordinate
import com.durhack.sharpshot.logic.Direction
import com.durhack.sharpshot.nodes.INode
import javafx.animation.Interpolator
import javafx.animation.Transition
import javafx.animation.TranslateTransition
import javafx.beans.InvalidationListener
import javafx.beans.value.ObservableLongValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.input.MouseButton
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.StrokeType
import javafx.util.Duration
import tornadofx.Fragment
import tornadofx.onChange
import tornadofx.pane
import java.math.BigInteger
import java.util.*
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

class ContainerView(val container: Container,
                    val tickRateProp: ObservableLongValue,
                    private val getUiSelectedNode: () -> INode?) : Fragment() {
    val running = container.running
    val outputs: ObservableList<BigInteger> = FXCollections.emptyObservableList()

    private var animating = false
    private var timer = Timer()
    private var tickRateChanged = false

    override val root = pane {
        updateSize(this)
    }

    init {
        quickRender()

        tickRateProp.onChange {
            if (animating) {
                tickRateChanged = true
            }
        }

        container.widthProp.onChange {
            updateSize(root)
            quickRender()
        }

        container.heightProp.onChange {
            updateSize(root)
            quickRender()
        }

        container.nodes.addListener(InvalidationListener { quickRender() })
    }

    private fun updateSize(pane: Pane) {
        pane.minWidth = container.width * GRID_SIZE.toDouble()
        pane.maxWidth = pane.minWidth
        pane.minHeight = container.height * GRID_SIZE.toDouble()
        pane.maxHeight = pane.minHeight
    }

    fun addColumnRight() {
        container.width++
    }

    fun addColumnLeft() {
        val newNodes = container.nodes.mapKeys { (coord, _) ->
            coord.plus(Direction.RIGHT) //Shift everything right 1
        }
        container.nodes.clear()
        container.nodes.putAll(newNodes)

        container.width++
    }

    fun addRowBottom() {
        container.height++
    }

    fun addRowTop() {
        val newNodes = container.nodes.mapKeys { (coord, _) ->
            coord.plus(Direction.DOWN) //Shift everything down 1
        }
        container.nodes.clear()
        container.nodes.putAll(newNodes)

        container.height++
    }

    fun removeColumnLeft() {
        if (container.width <= 1) return

        val minX = container.nodes.map { it.key.x }.min()
        if (minX == null || minX > 0) {
            val newNodes = container.nodes.mapKeys { (coord, _) ->
                coord.plus(Direction.LEFT) //Shift everything left 1
            }
            container.nodes.clear()
            container.nodes.putAll(newNodes)

            container.width--
        }
    }

    fun removeColumnRight() {
        if (container.width <= 1) return

        val maxX = container.nodes.map { it.key.x }.max()
        if (maxX == null || maxX < container.width - 1) {
            //There's nothing in the rightmost column
            container.width--
        }
    }

    fun removeRowBottom() {
        if (container.height <= 1) return

        val maxY = container.nodes.map { it.key.y }.max()
        if (maxY == null || maxY < container.height - 1) {
            //There's nothing in the bottom row
            container.height--
        }
    }

    fun removeRowTop() {
        if (container.height <= 1) return

        val minY = container.nodes.map { it.key.y }.min()
        if (minY == null || minY > 0) {
            val newNodes = container.nodes.mapKeys { (coord, _) ->
                coord + Direction.UP //Shift everything up 1
            }
            container.nodes.clear()
            container.nodes.putAll(newNodes)

            container.height--
        }
    }

    fun animate() {
        timer.cancel()

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (tickRateChanged) {
                    tickRateChanged = false

                    if (animating) { //Needed to ensure we can stop it
                        animate()
                    }
                }
                else {
                    outputs.addAll(tick())
                }
            }
        }, 0, tickRateProp.get())

        animating = true
    }

    private fun quickRender() {
        val newNodes = mutableListOf<Node>()

        container.nodes.forEach { (coordinate, node) ->
            val graphic = node.graphic()
            graphic.relocate((coordinate.x * GRID_SIZE).toDouble(), (coordinate.y * GRID_SIZE).toDouble())
            newNodes.add(graphic)
        }

        (0..(container.width - 1)).forEach { x ->
            for (y in 0..(container.height - 1)) {
                val background = emptyGraphic(Coordinate(x, y))
                background.relocate((x * GRID_SIZE).toDouble(), (y * GRID_SIZE).toDouble())
                newNodes.add(background)
            }
        }

        ui {
            root.children.clear()
            root.children.addAll(newNodes)
        }
    }

    private fun animatedRender() {
        quickRender()

        val bullets = mutableListOf<Node>()
        val transitions = mutableListOf<Transition>()
        val tickRate = tickRateProp.get().toDouble()

        for ((coordinate, bullet) in container.bullets) {
            val graphic = bullet.toGraphic()
            bullets.add(graphic)

            TranslateTransition(Duration.millis(tickRate)).run {
                val currentPos = Coordinate(coordinate.x, coordinate.y)
                val prevPos = currentPos.plus(bullet.direction.inverse)
                graphic.relocate((prevPos.x * GRID_SIZE).toDouble(), (prevPos.y * GRID_SIZE).toDouble())

                node = graphic
                toX = (bullet.direction.deltaX * GRID_SIZE).toDouble()
                toY = (bullet.direction.deltaY * GRID_SIZE).toDouble()
                interpolator = Interpolator.LINEAR
                isAutoReverse = false
                transitions.add(this)
            }
        }

        ui {
            root.children.addAll(bullets)
            transitions.forEach(Transition::play)
        }
    }

    fun tick(): List<BigInteger?> {
        val outputs = container.tick()
        animatedRender()

        return outputs.map { (_, bullet) ->
            bullet.value
        }
    }

    fun reset() {
        animating = false
        timer.cancel()
        container.reset()
        quickRender()
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
                    }
                }
                else {
                    if (mouseEvent.button == MouseButton.PRIMARY) {
                        currentNode.rotate()
                        quickRender()
                    }
                    else if (mouseEvent.button == MouseButton.SECONDARY) {
                        container.nodes.remove(coordinate)
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
        outputs.clear()
        container.start(inputs)
    }
}

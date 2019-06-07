package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.core.control.CollisionReport
import com.durhack.sharpshot.core.state.BulletMovement
import com.durhack.sharpshot.core.state.Container
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.util.ui
import com.durhack.sharpshot.util.GRID_SIZE
import javafx.animation.Interpolator
import javafx.animation.Transition
import javafx.animation.TranslateTransition
import javafx.scene.Node
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.StrokeType
import javafx.util.Duration
import tornadofx.*

class ContainerView(val container: Container) : Fragment() {
    private val nodeLayer = pane {
        updateSize(this)
    }

    private val bulletLayer = pane {
        updateSize(this)
    }

    override val root = stackpane {
        add(nodeLayer)
        add(bulletLayer)
    }

    private fun updateSize(pane: Pane) {
        pane.minWidth = container.width * GRID_SIZE.toDouble()
        pane.maxWidth = pane.minWidth
        pane.minHeight = container.height * GRID_SIZE.toDouble()
        pane.maxHeight = pane.minHeight
    }

    fun render() {
        val toDisplay = mutableListOf<Node>()

        container.nodes.forEach { (coordinate, node) ->
            val graphic = node.graphic().position(coordinate)
            toDisplay.add(graphic)
        }

        (0..(container.width - 1)).forEach { x ->
            (0..(container.height - 1)).forEach { y ->
                val graphic = emptyGraphic().position(Coordinate(x, y))
                toDisplay.add(graphic)
            }
        }

        container.bullets.forEach {
            val graphic = it.toGraphic()
            graphic.position(it.coordinate)
            toDisplay.add(graphic)
        }

        ui {
            nodeLayer.children.clear()
            nodeLayer.children += toDisplay
        }
    }

    fun animate(collisionReport: CollisionReport, lengthMs: Long) {
        val fullDuration = Duration.millis(lengthMs.toDouble())
        val halfDuration = fullDuration.divide(2.0)

        val fullTransitions = collisionReport.survived.map { getMoveTransition(it, 1.0, fullDuration) }

        val swapTransitions = collisionReport.swap.flatMap { (a, b) ->
            listOf(
                    getMoveTransition(a, 0.5, halfDuration),
                    getMoveTransition(b, 0.5, halfDuration)
                  )
        }

        val finalTransitions = collisionReport.final.flatMap { (a, b) ->
            listOf(
                    getMoveTransition(a, 1.0, fullDuration),
                    getMoveTransition(b, 1.0, fullDuration)
                  )
        }

        val transitions = fullTransitions + swapTransitions + finalTransitions

        ui {
            bulletLayer.children.clear()
            bulletLayer.children += transitions.map { it.node }
            transitions.forEach(Transition::play)
        }
    }

    private fun getMoveTransition(bulletMovement: BulletMovement,
                                  distanceMultiplier: Double,
                                  duration: Duration): TranslateTransition {
        val bullet = bulletMovement.bullet
        val movement = bulletMovement.movement
        val from = movement.from
        val node = bullet.toGraphic()
        node.position(from)
        val transition = TranslateTransition(duration)
        transition.toX = bullet.direction.deltaX * GRID_SIZE * distanceMultiplier
        transition.toY = bullet.direction.deltaY * GRID_SIZE * distanceMultiplier
        transition.interpolator = Interpolator.LINEAR
        transition.isAutoReverse = false
        return transition
    }

    private fun emptyGraphic() =
            Rectangle(GRID_SIZE.toDouble(), GRID_SIZE.toDouble(), Color.TRANSPARENT).apply {
                stroke = Color.GRAY
                strokeWidth = 0.5
                strokeType = StrokeType.CENTERED
            }

    private fun Node.position(coordinate: Coordinate): Node {
        val x = (coordinate.x * GRID_SIZE).toDouble()
        val y = (coordinate.y * GRID_SIZE).toDouble()
        relocate(x, y)
        return this
    }
}
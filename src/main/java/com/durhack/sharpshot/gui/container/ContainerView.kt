package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.core.control.CollisionReport
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.core.state.tick.BulletMovement
import com.durhack.sharpshot.gui.graphics.BulletGraphic
import com.durhack.sharpshot.gui.graphics.EmptyGraphic
import com.durhack.sharpshot.gui.graphics.GraphicsRegistry
import com.durhack.sharpshot.gui.util.ui
import com.durhack.sharpshot.util.MAX_SCALE
import com.durhack.sharpshot.util.MIN_SCALE
import com.durhack.sharpshot.util.container
import javafx.animation.Interpolator
import javafx.animation.Transition
import javafx.animation.TranslateTransition
import javafx.scene.Node
import javafx.util.Duration
import tornadofx.*
import kotlin.math.max
import kotlin.math.min

class ContainerView() : View() {
    var scale: Double = 48.0
        set(value){
            field = max(min(value, MAX_SCALE), MIN_SCALE)
            render()
        }

    private val nodeLayer = pane {}
    private val bulletLayer = pane {}

    override val root = stackpane {
        add(nodeLayer)
        add(bulletLayer)
    }

    init {
        render()

        root.setOnMouseClicked {
            println("Button: ${it.button}, X: ${it.x}, Y: ${it.y}, X2: ${it.sceneX}, Y2: ${it.sceneY}")
        }
    }

    fun render() {
        root.minWidth = container.width * scale
        root.maxWidth = root.minWidth
        root.minHeight = container.height * scale
        root.maxHeight = root.minHeight

        val toDisplay = mutableListOf<Node>()

        container.nodes.forEach { (coordinate, node) ->
            val graphic = GraphicsRegistry.getGraphic(coordinate, scale, node)
            toDisplay.add(graphic)
        }

        (0..(container.width - 1)).forEach { x ->
            (0..(container.height - 1)).forEach { y ->
                val graphic = EmptyGraphic(Coordinate(x,y), scale)
                toDisplay.add(graphic)
            }
        }

        container.bullets.forEach {bullet ->
            val graphic = BulletGraphic(bullet = bullet, scale = scale)
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
        val node = BulletGraphic(bullet, from, scale)
        val transition = TranslateTransition(duration, node)
        transition.toX = bullet.direction.deltaX * scale * distanceMultiplier
        transition.toY = bullet.direction.deltaY * scale * distanceMultiplier
        transition.interpolator = Interpolator.LINEAR
        transition.isAutoReverse = false
        return transition
    }
}
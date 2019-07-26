package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.core.control.CollisionReport
import com.durhack.sharpshot.core.state.tick.BulletMovement
import com.durhack.sharpshot.gui.input.layers.popovers.DragBox
import com.durhack.sharpshot.gui.input.layers.popovers.SelectionBox
import com.durhack.sharpshot.gui.util.FractionalCoordinate
import com.durhack.sharpshot.gui.util.ui
import com.durhack.sharpshot.util.MinMaxIntProperty
import com.durhack.sharpshot.util.globalContainer
import javafx.animation.Interpolator
import javafx.animation.Transition
import javafx.animation.TranslateTransition
import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.scene.Node
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.util.Duration
import tornadofx.*

class ContainerView : View() {
    companion object {
        val innerScaleProp: MinMaxIntProperty = MinMaxIntProperty(10, 50, 500)
        val scaleProp = innerScaleProp.ui()
        var scale by innerScaleProp
    }

    private val selectionBox: SelectionBox by inject()
    private val dragBox: DragBox by inject()

    private val nodeLayer: ContainerStaticView by inject()
    private val bulletLayer = pane {
        id = "Bullet Layer"
    }

    override val root = stackpane {
        id = "Container View"

        add(nodeLayer)
        add(bulletLayer)
        background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
    }

    val width get() = (scale * globalContainer.width + 1).toDouble()
    val height get() = (scale * globalContainer.height + 1).toDouble()

    init {
        render()
        innerScaleProp.onChange { render() }
    }

    fun render() {
        val bullets = mutableListOf<Node>()
        globalContainer.bullets.forEach { bullet ->
            val graphic = BulletGraphic(bullet = bullet, scale = scale)
            bullets.add(graphic)
        }

        ui {
            root.minWidth = width
            root.maxWidth = width
            root.minHeight = height
            root.maxHeight = height
            nodeLayer.render()
            bulletLayer.clear()
            bulletLayer.children += bullets

            //This mitigates the issue where the drag box wouldn't appear at the mouse location after zooming
            dragBox.hide()
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

    fun getCoord(point: Point2D) = getCoord(point.x, point.y)

    fun getCoord(x: Double, y: Double): FractionalCoordinate {
        val scale = ContainerView.innerScaleProp.get()
        val xClicked = x / scale
        val yClicked = y / scale
        return FractionalCoordinate(xClicked, yClicked)
    }

    fun getPoint(coord: FractionalCoordinate): Point2D {
        val pointX = coord.x * scale
        val pointY = coord.y * scale
        return Point2D(pointX, pointY)
    }
}
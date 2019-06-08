package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.util.MyScrollPaneSkin
import com.durhack.sharpshot.util.clamp
import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.StackPane
import tornadofx.*
import java.lang.Math.pow
import kotlin.math.abs

class CenteredScrollPane(val containerView: ContainerView) : Fragment() {
    val ZOOM_PER_STEP: Double = 1.05
    val paddingAmount = 0e4
    var dragging = false

    val inner = stackpane {
        add(containerView)
        StackPane.setMargin(containerView.root, Insets(paddingAmount))
        alignment = Pos.CENTER

        addEventHandler(MouseEvent.ANY) {
            if (it.button != MouseButton.SECONDARY) {
                it.consume()
            }
        }

        add(containerView)
    }

    override val root = scrollpane {
        vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        isPannable = true
        isFitToHeight = true
        isFitToWidth = true

        skin = MyScrollPaneSkin(this)

        setOnMousePressed {
            if (it.button == MouseButton.SECONDARY) {
                dragging = true
            }
        }

        setOnMouseReleased {
            if (it.button == MouseButton.SECONDARY) {
                dragging = false
            }
        }

        addEventFilter(ScrollEvent.SCROLL) { event: ScrollEvent ->
            if (!dragging){
                val amount = abs(event.textDeltaY)

                if (amount != 0.0){
                    val zoomIn = event.deltaY > 0
                    val scaleFactor = when {
                        zoomIn -> pow(ZOOM_PER_STEP, amount)
                        else -> pow(1 / ZOOM_PER_STEP, amount)
                    }

                    zoom(event.x, event.y, scaleFactor)
                }
            }

            event.consume()
        }

        add(inner)
    }

    private val containerSize: Point2D get() = Point2D(containerView.width, containerView.height)
    private val rootSize: Point2D get() = Point2D(root.width, root.height)
    private operator fun Point2D.times(oth: Double): Point2D = multiply(oth)
    private operator fun Point2D.minus(oth: Point2D): Point2D = subtract(oth)
    private operator fun Point2D.div(oth: Point2D): Point2D = Point2D(x / oth.x, y / oth.y)
    private fun Point2D.clamp(min: Number, max: Number): Point2D = Point2D(x.clamp(min, max), y.clamp(min, max))

    private fun zoom(mouseX: Double, mouseY: Double, factor: Double) {
        val sceneMouseLocation = Point2D(mouseX, mouseY)
        val containerMouseLocation = containerView.root.sceneToLocal(sceneMouseLocation)

        val oldSize = containerSize
        scale(factor)
        val newSize = containerSize
        val realFactor = newSize.x / oldSize.x
        if(realFactor == 1.0) return

        val requiredMouseLocation = containerMouseLocation * realFactor
        val scrollingRequired = requiredMouseLocation - sceneMouseLocation

        val slack = newSize - rootSize
        val fractionalScrollingRequired = scrollingRequired / slack
        val clamped = fractionalScrollingRequired.clamp(0, 1)
        root.hvalue = clamped.x
        root.vvalue = clamped.y
    }

    private fun scale(factor: Double) {
        containerView.scale *= factor
        containerView.render()
    }
}

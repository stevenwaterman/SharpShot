package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.util.container
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
    val paddingAmount = 1e4
    var dragging = false

    val inner = stackpane {
        add(containerView)
        StackPane.setMargin(containerView.root, Insets(paddingAmount))
        alignment = Pos.CENTER

        addEventHandler(MouseEvent.ANY) { event ->
            when {
                event.button != MouseButton.SECONDARY -> event.consume()
                event.eventType == MouseEvent.DRAG_DETECTED -> dragging = true
                event.eventType == MouseEvent.MOUSE_RELEASED -> dragging = false
            }
        }

        add(containerView)
    }

    override val root = scrollpane {
        vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        isPannable = true

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
        setOnKeyPressed {
            if (it.isControlDown) centerScroll()
        }
    }

    init {
        centerScroll()
    }

    fun centerScroll() {
        root.hvalue = 0.5
        root.vvalue = 0.5
    }

    fun zoom(mouseX: Double, mouseY: Double, factor: Double) {
        val oldWidth = containerView.scale * container.width

        //Scale the container
        containerView.scale *= factor
        containerView.render()

        //Scroll so it looks like we zoom around the mouse
        val newWidth = containerView.scale * container.width
        val newHeight = containerView.scale * container.height

        val increase = (newWidth / oldWidth) - 1

        val topLeftLocation = containerView.root.localToScene(Point2D(0.0, 0.0))
        val xDelta = mouseX - topLeftLocation.x
        val yDelta = mouseY - topLeftLocation.y

        val scrollH = 1.136 * increase * xDelta / (newWidth + paddingAmount * 2)
        val scrollV = 1.068 * increase * yDelta / (newHeight + paddingAmount * 2)

        root.hvalue += scrollH
        root.vvalue += scrollV
    }
}
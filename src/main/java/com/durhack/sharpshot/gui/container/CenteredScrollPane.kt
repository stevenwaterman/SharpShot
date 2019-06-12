package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.util.*
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

class CenteredScrollPane : View() {
    private val containerView: ContainerView by inject()
    private val zoomPerStep: Double = 1.05
    private val paddingAmount = 0e4
    private var dragging = false

    private val inner = stackpane {
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
        skin = HackScrollPaneSkin(this)

        setOnMousePressed { if (it.button == MouseButton.SECONDARY) dragging = true }
        setOnMouseReleased { if (it.button == MouseButton.SECONDARY) dragging = false }

        addEventFilter(ScrollEvent.SCROLL) { event: ScrollEvent ->
            if(!dragging) zoom(event.x, event.y, event.textDeltaY)
            event.consume()
        }

        add(inner)
    }

    private val containerSize: Point2D get() = Point2D(containerView.width, containerView.height)
    private val rootSize: Point2D get() = Point2D(root.width, root.height)

    private fun zoom(mouseX: Double, mouseY: Double, textDeltaY: Double) {
        val sceneMouseLocation = Point2D(mouseX, mouseY)
        val containerMouseLocation = containerView.root.sceneToLocal(sceneMouseLocation)

        val oldSize = containerSize
        val factor = pow(zoomPerStep, textDeltaY)
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

package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.input.layers.LayerContainer
import com.durhack.sharpshot.util.*
import javafx.geometry.Point2D
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.ScrollPane
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.StackPane
import tornadofx.*
import java.lang.Math.pow
import kotlin.math.roundToInt

class ScrollZoomPane : View() {
    private val layers: LayerContainer by inject()
    private val zoomPerStep: Double = 1.05
    private var dragging = false
    private val containerView: ContainerView by inject()

    private val inner: StackPane = stackpane {
        id = "Scroll Zoom Pane"
        add(layers)
        alignment = Pos.CENTER

        addEventHandler(MouseEvent.ANY) {
            if (it.button != MouseButton.SECONDARY) it.consume()
        }
    }


    override val root = scrollpane {
        id = "Container Scroll Pane"
        vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        isPannable = true
        isFitToHeight = true
        isFitToWidth = true
        skin = HackScrollPaneSkin(this)
        content = inner

        setOnMousePressed { if (it.button == MouseButton.SECONDARY) dragging = true }
        setOnMouseReleased { if (it.button == MouseButton.SECONDARY) dragging = false }

        addEventFilter(ScrollEvent.SCROLL) { event: ScrollEvent ->
            if (!dragging) zoom(event.x, event.y, event.textDeltaY)
            event.consume()
        }
    }

    private val containerSize: Point2D get() = Point2D(containerView.width, containerView.height)
    private val rootSize: Point2D get() = Point2D(root.width, root.height)

    private fun zoom(mouseX: Double, mouseY: Double, textDeltaY: Double) {
        val scrollPaneLocation = Point2D(mouseX, mouseY)

        var currentParent: Parent = containerView.root
        val parentChain = mutableListOf<Parent>()

        while (currentParent != root) {
            parentChain.add(currentParent)
            currentParent = currentParent.parent
        }

        var containerLocation = scrollPaneLocation
        parentChain.reversed().forEach {
            containerLocation = it.parentToLocal(containerLocation)
        }

        val oldSize = containerSize
        val factor = pow(zoomPerStep, textDeltaY)
        scale(factor)
        val newSize = containerSize
        val realFactor = newSize.x / oldSize.x
        if (realFactor == 1.0) return

        val wrapperAdjustment = containerView.root.localToParent(Point2D(0.0, 0.0))

        val requiredMouseLocation = containerLocation * realFactor
        val scrollingRequired = requiredMouseLocation - scrollPaneLocation
        val totalScroll = scrollingRequired + wrapperAdjustment

        val slack = (wrapperAdjustment * 2) + newSize - rootSize
        val fractionalScrollingRequired = totalScroll / slack
        val clamped = fractionalScrollingRequired.clamp(0.0, 1.0)
        root.hvalue = clamped.x
        root.vvalue = clamped.y
    }

    private fun scale(factor: Double) {
        ContainerView.scale = (ContainerView.scale * factor).roundToInt()
        containerView.render()
    }
}

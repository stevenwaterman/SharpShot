package com.durhack.sharpshot.gui.input.layers.popovers

import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerStaticRenderer
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.util.CoordinateRange2D
import com.durhack.sharpshot.util.globalContainer
import com.durhack.sharpshot.util.globalExtractProp
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*

class PasteHover : View() {
    val renderer = ContainerStaticRenderer()

    val containerView: ContainerView by inject()

    companion object {
        val pastingProp = SimpleBooleanProperty(false)
        var pasting by pastingProp

        val lastCoordProp = SimpleObjectProperty<PasteCoord>(null)
        var lastCoord by lastCoordProp

        val validPasteAreaProp = globalExtractProp.objectBinding(globalExtractProp) { extract ->
            extract ?: return@objectBinding CoordinateRange2D(IntRange.EMPTY, IntRange.EMPTY)

            val extractWidth = extract.width
            val extractHeight = extract.height

            val containerWidth = globalContainer.width
            val containerHeight = globalContainer.height

            val xSlack = containerWidth - extractWidth
            val ySlack = containerHeight - extractHeight

            return@objectBinding CoordinateRange2D(0..xSlack, 0..ySlack)
        }
        val validPasteArea by validPasteAreaProp

        fun reset() {
            pasting = false
            lastCoord = null
        }
    }

    init {
        lastCoordProp.onChange { pasteCoord ->
            val (coord, valid) = pasteCoord ?: return@onChange
            val point = containerView.getPoint(coord.fractional)
            root.layoutX = point.x
            root.layoutY = point.y

            if (valid) {
                root.background = Background(BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY))
            }
            else {
                root.background = Background(BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY))
            }
        }

        globalExtractProp.onChange {
            renderer.renderExtract(ContainerView.scale)
        }

        ContainerView.scaleProp.onChange {
            renderer.renderExtract(ContainerView.scale)
        }
    }

    override val root = stackpane {
        id = "Paste Hover"
        visibleWhen(pastingProp.and(lastCoordProp.isNotNull))
        opacity = 0.5

        add(renderer)
    }
}

data class PasteCoord(val coord: Coordinate, val valid: Boolean)
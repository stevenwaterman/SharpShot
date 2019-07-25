package com.durhack.sharpshot.gui.container.input.layers.popovers

import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.util.CoordinateRange2D
import com.durhack.sharpshot.gui.util.FractionalCoordinate
import com.durhack.sharpshot.gui.util.addClickHandler
import com.durhack.sharpshot.util.container
import javafx.geometry.Insets
import javafx.scene.input.MouseButton
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.math.*

class SelectionMenu : View() {
    private val containerView: ContainerView by inject()
    private var selected: CoordinateRange2D? = null

    init {
        container.widthProp.addListener { _ -> hide() }
        container.heightProp.addListener { _ -> hide() }
    }

    override val root = pane {
        id = "Selection Menu"

        background = Background(BackgroundFill(Color(1.0, 0.0, 0.0, 0.2), CornerRadii.EMPTY, Insets.EMPTY))
        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(3.0)))

        addClickHandler {
            if (it.button == MouseButton.PRIMARY) {
                it.consume()
            }
            if (it.button == MouseButton.SECONDARY) {
                hide()
            }
        }

        setOnMouseDragged {
            if (it.button == MouseButton.SECONDARY) {
                it.consume()
            }
        }

        setOnMousePressed {
            it.consume()
        }
    }

    fun hide() {
        selected = null
        render()
    }

    fun show(start: FractionalCoordinate, end: FractionalCoordinate) {
        val startX = start.x
        val endX = end.x
        val xRange = intRange(startX, endX)
        val validX = 0..container.width
        val clampX = xRange.intersect(validX)

        val startY = start.y
        val endY = end.y
        val yRange = intRange(startY, endY)
        val validY = 0..container.height
        val clampY = yRange.intersect(validY)

        selected = CoordinateRange2D(clampX, clampY)
        render()
    }

    private fun intRange(a: Double, b: Double): IntRange {
        val min = min(a, b)
        val max = max(a, b)

        val floor = floor(min).roundToInt()
        val ceil = ceil(max).roundToInt()

        return floor..ceil
    }

    private fun IntRange.intersect(oth: IntRange): IntRange {
        val min1 = first
        val min2 = oth.first
        val min = max(min1, min2)

        val max1 = endInclusive
        val max2 = oth.endInclusive
        val max = min(max1, max2)

        return min..max
    }

    fun render() {
        val capt = selected
        if (capt == null) {
            root.isVisible = false
        }
        else {
            root.isVisible = true

            val xRange = capt.xRange
            val xCoordMin = xRange.first
            val xCoordMax = xRange.endInclusive

            val yRange = capt.yRange
            val yCoordMin = yRange.first
            val yCoordMax = yRange.endInclusive

            val minCoord = FractionalCoordinate(xCoordMin, yCoordMin)
            val maxCoord = FractionalCoordinate(xCoordMax, yCoordMax)

            val minPoint = containerView.getPoint(minCoord)
            val maxPoint = containerView.getPoint(maxCoord)

            val xMin = minPoint.x
            val xMax = maxPoint.x

            val yMin = minPoint.y
            val yMax = maxPoint.y

            root.layoutX = xMin
            root.layoutY = yMin

            val width = xMax - xMin
            val height = yMax - yMin

            //TODO this is a horrible hack but for some reason after you resize it to be the same size as the view popover layer, it never resizes during the layout passes so we have to manually set the size too. Before it breaks, the manual resize gets overriden by the automatic one, so we need to prefsize etc too.
            root.resize(width, height)
            root.setMinSize(width, height)
            root.setPrefSize(width, height)
            root.setMaxSize(width, height)
        }
    }
}
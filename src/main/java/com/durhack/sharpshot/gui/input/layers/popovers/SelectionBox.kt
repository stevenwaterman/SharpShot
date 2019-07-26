package com.durhack.sharpshot.gui.input.layers.popovers

import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.util.FractionalCoordinate
import com.durhack.sharpshot.util.globalContainer
import com.durhack.sharpshot.util.globalSelection
import com.durhack.sharpshot.util.globalSelectionProp
import javafx.geometry.Insets
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*

class SelectionBox : View() {
    private val containerView: ContainerView by inject()

    init {
        globalContainer.widthProp.addListener { _ -> globalSelection = null }
        globalContainer.heightProp.addListener { _ -> globalSelection = null }
        globalSelectionProp.addListener { _ -> render() }
    }

    override val root = borderpane {
        id = "Selection Menu"

        isVisible = false
        background = Background(BackgroundFill(Color(1.0, 0.0, 0.0, 0.2), CornerRadii.EMPTY, Insets.EMPTY))
        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(3.0)))
    }

    private fun render() {
        val capt = globalSelection
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
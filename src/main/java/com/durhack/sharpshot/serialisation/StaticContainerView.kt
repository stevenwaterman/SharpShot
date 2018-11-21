package com.durhack.sharpshot.serialisation

import com.durhack.sharpshot.GRID_SIZE
import com.durhack.sharpshot.logic.Container
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.StrokeType
import tornadofx.*

class StaticContainerView(val container: Container) : Fragment() {
    override val root = pane {
        minWidth = container.width * GRID_SIZE.toDouble()
        maxWidth = minWidth
        minHeight = container.height * GRID_SIZE.toDouble()
        maxHeight = minHeight

        container.nodes.forEach { (coordinate, node) ->
            val graphic = node.graphic()
            graphic.relocate((coordinate.x * GRID_SIZE).toDouble(), (coordinate.y * GRID_SIZE).toDouble())
            add(graphic)
        }

        (0..(container.width - 1)).forEach { x ->
            (0..(container.height - 1)).forEach { y ->
                val graphic = emptyGraphic
                graphic.relocate((x * GRID_SIZE).toDouble(), (y * GRID_SIZE).toDouble())
                add(graphic)
            }
        }
    }

    private val emptyGraphic
        get() = Rectangle(GRID_SIZE.toDouble(), GRID_SIZE.toDouble(), Color.TRANSPARENT)
                .run {
                    stroke = Color.GRAY
                    strokeWidth = 0.5
                    strokeType = StrokeType.CENTERED
                    return@run this
                }
}

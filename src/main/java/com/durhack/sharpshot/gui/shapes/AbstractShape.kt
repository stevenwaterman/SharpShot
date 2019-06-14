package com.durhack.sharpshot.gui.shapes

import com.durhack.sharpshot.core.state.Direction
import javafx.geometry.HPos
import javafx.scene.CacheHint
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Polygon
import javafx.scene.text.Font
import tornadofx.*

abstract class AbstractShape(points: List<Pair<Number, Number>>,
                             scale: Double,
                             rotation: Direction,
                             color: Color,
                             labelText: String?) : StackPane() {
    init {
        val normalisedPoints = points
                .flatMap { (a, b) -> listOf(a, b) }
                .map { it.toDouble() * scale }

        val polygon = Polygon(*normalisedPoints.toDoubleArray())
        polygon.rotate = rotation.degrees
        polygon.fill = color
        add(polygon)

        if (labelText != null) {
            val label = Label(labelText)
            label.font = Font(scale * 0.5)
            add(label)
            GridPane.setHalignment(label, HPos.CENTER)
        }

        isCache = true
        cacheHint = CacheHint.SPEED
    }
}
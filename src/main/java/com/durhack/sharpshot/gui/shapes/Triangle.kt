package com.durhack.sharpshot.gui.shapes

import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.util.GRID_SIZE
import javafx.geometry.HPos
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Polygon

open class Triangle(rotation: Direction, color: Color, s: String?) : GridPane() {
    init {
        val polygon = Polygon(0.0,
                              GRID_SIZE.toDouble(),
                              GRID_SIZE.toDouble(),
                              GRID_SIZE.toDouble(),
                              GRID_SIZE * 0.5,
                              0.0)
        polygon.rotate = rotation.degrees
        polygon.fill = color
        add(polygon, 0, 0)
        if (s != null) {
            val label = Label(s)
            add(label, 0, 0)
            GridPane.setHalignment(label, HPos.CENTER)
        }
    }
}

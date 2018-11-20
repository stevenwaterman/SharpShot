package com.durhack.sharpshot.gui

import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.GRID_SIZE
import javafx.geometry.HPos
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Polygon

class Diamond(rotation: Direction, color: Color, s: String?) : GridPane() {
    init {
        val polygon = Polygon(0.0, GRID_SIZE* 0.5, GRID_SIZE* 0.5, 0.0, GRID_SIZE.toDouble(), GRID_SIZE*0.5, GRID_SIZE*0.5, GRID_SIZE.toDouble())
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

package com.durhack.sharpshot.gui

import com.durhack.sharpshot.Direction
import javafx.geometry.HPos
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Polygon

class Triangle(rotation: Direction, color: Color, s: String?) : GridPane() {
    init {
        val polygon = Polygon(0.0, 32.0, 32.0, 32.0, 16.0, 0.0)
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

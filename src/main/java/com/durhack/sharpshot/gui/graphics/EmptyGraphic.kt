package com.durhack.sharpshot.gui.graphics

import com.durhack.sharpshot.core.state.Coordinate
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.StrokeType

class EmptyGraphic(coordinate: Coordinate, scale: Double) :
        Graphic(coordinate, scale, {
            val rectangle = Rectangle(scale, scale, Color.TRANSPARENT)
            rectangle.stroke = Color.GRAY
            rectangle.strokeWidth = 0.5
            rectangle.strokeType = StrokeType.CENTERED
            rectangle
        })
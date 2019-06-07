package com.durhack.sharpshot.gui.shapes

import com.durhack.sharpshot.core.state.Direction
import javafx.scene.paint.Color

class Square(scale: Double, rotation: Direction, color: Color, labelText: String?) : AbstractShape(
        listOf(
                0 to 0,
                0 to 1,
                1 to 1,
                1 to 0
              ),
        scale, rotation, color, labelText)

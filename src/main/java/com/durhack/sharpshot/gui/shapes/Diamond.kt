package com.durhack.sharpshot.gui.shapes

import com.durhack.sharpshot.core.state.Direction
import javafx.scene.paint.Color

class Diamond(scale: Double, rotation: Direction, color: Color, labelText: String?) :
        AbstractShape(listOf(
                0 to 0.5,
                0.5 to 0,
                1 to 0.5,
                0.5 to 1),
                      scale,
                      rotation,
                      color,
                      labelText) {
}

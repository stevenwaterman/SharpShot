package com.durhack.sharpshot.gui.graphics

import com.durhack.sharpshot.core.state.Bullet
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.shapes.Draw
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color

class BulletGraphic(val bullet: Bullet, coordinate: Coordinate = bullet.coordinate, scale: Int) : Canvas() {
    init {
        val scaleDouble = scale.toDouble()
        width = scaleDouble
        height = scaleDouble
        Draw.bullet(graphicsContext2D,
                    bullet.direction,
                    scaleDouble / 4,
                    scaleDouble / 4,
                    (scale + 1) / 2,
                    Color.DARKSALMON)
        val bulletValue = bullet.value
        if (bulletValue != null) {
            Draw.text(graphicsContext2D, bullet.value?.toString() ?: "", scaleDouble / 4, scaleDouble / 4, scale / 2)
        }

        layoutX = coordinate.x * scale - layoutBounds.minX
        layoutY = coordinate.y * scale - layoutBounds.minY
    }
}
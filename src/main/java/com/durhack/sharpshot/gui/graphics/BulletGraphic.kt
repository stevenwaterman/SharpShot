package com.durhack.sharpshot.gui.graphics

import com.durhack.sharpshot.core.state.Bullet
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.shapes.Square
import javafx.scene.paint.Color

class BulletGraphic(val bullet: Bullet, coordinate: Coordinate = bullet.coordinate, scale: Double) :
        Graphic(coordinate, scale, {
            Square(scale * 0.5, bullet.direction, Color.DARKGRAY, bullet.value?.toString() ?: "")
        })
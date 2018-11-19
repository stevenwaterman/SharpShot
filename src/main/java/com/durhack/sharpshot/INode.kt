package com.durhack.sharpshot

import javafx.scene.Node

import java.math.BigInteger

abstract class INode {
    var rotation = Direction.UP

    fun rotate() {
        rotation = rotation.clockwise
    }

    abstract fun run(bullet: Bullet): Map<Direction, BigInteger?>
    abstract fun toGraphic(): Node
    abstract fun reset()
}
package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import javafx.scene.Node

import java.math.BigInteger

abstract class INode {
    var rotation = Direction.UP

    fun rotate() {
        rotation = rotation.clockwise
    }

    abstract fun run(bullet: Bullet): Map<Direction, BigInteger?>
    abstract fun graphic(): Node
    abstract fun reset()

    open val factory: () -> INode? = {this::class.java.newInstance()}
    abstract val tooltip: String
}
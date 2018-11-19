package com.durhack.sharpshot

import javafx.scene.Node

import java.math.BigInteger

interface INode {
    val rotation: Direction

    fun rotateClockwise()

    fun run(bullet: Bullet): Map<Direction, BigInteger?>

    fun toGraphic(): Node

    fun reset()
}
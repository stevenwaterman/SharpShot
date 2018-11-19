package com.durhack.sharpshot.nodes.math

import com.durhack.sharpshot.gui.Triangle
import javafx.scene.Node
import javafx.scene.paint.Color

import java.math.BigInteger

class SubNode : MathNode() {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? {
        return val1.subtract(val2)
    }

    override fun toString(): String {
        return "Subtract"
    }

    override fun toGraphic(): Node {
        return Triangle(rotation, Color.web("#7700FF"), "-")
    }
}

package com.durhack.sharpshot.nodes.math

import com.durhack.sharpshot.gui.Triangle
import javafx.scene.Node
import javafx.scene.paint.Color

import java.math.BigInteger

class DivNode : MathNode() {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? {
        return if (val2 == BigInteger.ZERO) {
            null
        }
        else {
            val1.divide(val2)
        }
    }

    override fun toString(): String {
        return "Divide"
    }

    override fun toGraphic(): Node {
        return Triangle(rotation, Color.web("#CC66FF"), "÷")
    }
}

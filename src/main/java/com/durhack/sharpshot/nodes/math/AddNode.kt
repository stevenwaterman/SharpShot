package com.durhack.sharpshot.nodes.math

import com.durhack.sharpshot.gui.Triangle
import javafx.scene.Node
import javafx.scene.paint.Color

import java.math.BigInteger

class AddNode : MathNode() {
    public override fun operation(val1: BigInteger, val2: BigInteger): BigInteger? {
        return val1.add(val2)
    }

    override fun toGraphic(): Node {
        return Triangle(rotation, Color.web("#add8e6"), "+")
    }

    override fun toString(): String {
        return "Add"
    }
}

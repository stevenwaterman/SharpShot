package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.other.ConstantNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import com.google.gson.JsonObject
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import java.math.BigInteger

class ConstantNodeEntry() : AbstractNodeRegistryEntry<ConstantNode>(
        ConstantNode(BigInteger.ONE, Direction.UP),
        "In Node",
        "Whenever a bullet passes through, release another bullet with pre-set value"
                                                                   ) {
    override fun create(json: JsonObject): ConstantNode {
        val valueString = json["value"].asString
        val value = when (valueString) {
            "null" -> null
            else -> BigInteger(valueString)
        }

        val rotation = json["direction"].asInt
        val direction = Direction.ofQuarters(rotation)

        return ConstantNode(value, direction)
    }

    override fun toJson(node: ConstantNode): JsonObject {
        val json = super.toJson(node).asJsonObject
        json.addProperty("value", node.value?.toString() ?: "null")
        return json
    }

    private val color = Color.GREEN
    override fun draw(node: ConstantNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, color)
        Draw.text(gc, node.value?.toString() ?: "", x, y, scale)
    }
}
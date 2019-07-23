package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.input.InputNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import com.google.gson.JsonObject
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class InNodeEntry : RegistryEntry<InputNode>(
        InputNode(1, Direction.UP),
        "Input",
        "Provides Input at program start and every time a bullet passes through"
                                            ) {
    override fun create(json: JsonObject): InputNode {
        val indexString = json["index"].asString
        val index = when (indexString) {
            "null" -> null
            else -> indexString.toInt()
        }

        val rotation = json["direction"].asInt
        val direction = Direction.ofQuarters(rotation)

        return InputNode(index, direction)
    }

    override fun toJson(node: InputNode): JsonObject {
        val json = super.toJson(node).asJsonObject
        json.addProperty("index", node.index?.toString() ?: "null")
        return json
    }

    override fun draw(node: InputNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Int) {
        Draw.triangle(gc, node.direction, x, y, scale, Color.GOLD)
        val text = when (node.index) {
            null -> "E"
            else -> "IN${node.index}"
        }
        Draw.text(gc, text, x, y, scale)
    }
}
package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.input.InNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.menus.createnode.nodeforms.InNodeForm
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.registry.RegistryEntry
import com.google.gson.JsonObject
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class InNodeEntry() : RegistryEntry<InNode>(
        InNode(1, Direction.UP),
        "Input",
        "Provides Input at program start and every time a bullet passes through"
                                           ) {
    override fun create(json: JsonObject): InNode {
        val indexString = json["index"].asString
        val index = when (indexString) {
            "null" -> null
            else -> indexString.toInt()
        }

        val rotation = json["direction"].asInt
        val direction = Direction.ofQuarters(rotation)

        return InNode(index, direction)
    }

    override fun toJson(node: InNode): JsonObject {
        val json = super.toJson(node).asJsonObject
        json.addProperty("index", node.index?.toString() ?: "null")
        return json
    }

    override fun draw(node: InNode,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double) {
        Draw.triangle(gc, node.direction, x, y, scale, Color.YELLOW)
        val text = when(node.index) {
            null -> "E"
            else -> "IN${node.index}"
        }
        Draw.text(gc, text, x, y, scale)
    }

    override fun getNodeForm(close: () -> Unit, success: (InNode) -> Unit) = InNodeForm(close, success)
}
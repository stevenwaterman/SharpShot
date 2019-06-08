package com.durhack.sharpshot.registry.entries

import com.durhack.sharpshot.core.nodes.input.InNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.AbstractNodeRegistryEntry
import com.google.gson.JsonObject
import javafx.scene.canvas.GraphicsContext

class InNodeEntry() : AbstractNodeRegistryEntry<InNode>(
        InNode(1, Direction.UP),
        "In Node",
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
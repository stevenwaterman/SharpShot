package com.durhack.sharpshot.core.nodes.input

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.math.BigInteger

class InNode(val index: Int?) : AbstractInputNode() {
    private var input: BigInteger? = null

    override fun initialise(inputs: List<BigInteger?>): Pair<Direction, BigInteger?>? {
        input = if (index == null) null else inputs.getOrNull(index)
        return Direction.UP to input
    }

    override fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?> {
        return mapOf(
                relativeDirection to value,
                Direction.UP to input
                    )
    }

    override fun reset() {}

    override val type = "initialise"

    override val tooltip = "Provides Input at program start and every time a bullet passes through"

    override fun toJson(): JsonElement {
        val json = super.toJson().asJsonObject
        json.addProperty("index", index?.toString() ?: "null")
        return json
    }

    override val jsonFactory: (JsonObject) -> AbstractNode = { json ->
        val indexString = json["index"].asString
        val index = when (indexString) {
            "null" -> null
            else -> indexString.toInt()
        }

        val node = InNode(index)
        val rotation = json["direction"].asInt
        node.direction = Direction.ofQuarters(rotation)
        node
    }
}

package com.durhack.sharpshot.core.nodes.other

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.math.BigInteger

class ConstantNode(var value: BigInteger?) : AbstractNode() {
    /**
     * If both the bullet and the output are in the same direction, the second element in the map overrides the first
     * so the initialise bullet is destroyed
     */
    override fun process(relativeDirection: Direction, value: BigInteger?) =
            mutableMapOf(relativeDirection to value, Direction.UP to value)

    override fun reset() {}

    override val type = "constant"

    override val tooltip = "Whenever a bullet passes through, release another bullet with pre-set value"

    override fun toJson(): JsonElement {
        val json = super.toJson().asJsonObject
        json.addProperty("value", value?.toString() ?: "null")
        return json
    }

    override val jsonFactory: (JsonObject) -> ConstantNode = { json ->
        val valueString = json["value"].asString
        val value = when (valueString) {
            "null" -> null
            else -> BigInteger(valueString)
        }

        val node = ConstantNode(value)
        val rotation = json["direction"].asInt
        node.direction = Direction.ofQuarters(rotation)
        node
    }
}

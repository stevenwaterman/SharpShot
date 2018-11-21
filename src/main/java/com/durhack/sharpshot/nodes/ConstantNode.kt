package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.Bullet
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.gui.shapes.Triangle
import com.durhack.sharpshot.gui.util.getNumberInput
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import javafx.scene.paint.Color
import java.math.BigInteger

class ConstantNode(var value: BigInteger?) : INode() {
    /**
     * If both the bullet and the output are in the same direction, the second element in the map overrides the first
     * so the input bullet is destroyed
     */
    override fun run(bullet: Bullet): Map<Direction, BigInteger?> =
            mutableMapOf(bullet.direction to bullet.value,
                         Direction.UP to value
                        )

    override fun graphic() = Triangle(rotation,
                                                                       Color.LIMEGREEN,
                                                                       value?.toString() ?: "")
    override fun reset() {}

    override val type = "constant"

    override val tooltip = "Whenever a bullet passes through, release another bullet with pre-set value"
    override val factory = {
        val value = getNumberInput("Enter Input Index",
                                                                  "Blank to shoot empty bullet at start\nArguments are 0-indexed")
        if (value.isPresent) {
            val string = value.get()
            when {
                string.isBlank() -> ConstantNode(null)
                else -> ConstantNode(BigInteger(string))
            }
        }
        else {
            null
        }
    }

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
        val rotation = json["rotation"].asInt
        node.rotation = Direction.ofQuarters(rotation)
        node
    }
}

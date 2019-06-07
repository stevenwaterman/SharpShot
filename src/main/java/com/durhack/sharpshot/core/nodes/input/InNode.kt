package com.durhack.sharpshot.core.nodes.input

import com.durhack.sharpshot.core.nodes.INode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.shapes.Triangle
import com.durhack.sharpshot.gui.util.getNumberInput
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import javafx.scene.paint.Color
import java.math.BigInteger

class InNode(private val index: Int?) : AbstractInputNode() {
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

    override fun graphic() = Triangle(direction,
                                      Color.web("#FFFF00"),
                                      "IN${index ?: ""}")

    override fun reset() {}

    override val type = "initialise"

    override val tooltip = "Provides Input at program start and every time a bullet passes through"
    override val factory = {
        val index = getNumberInput("Enter Input Index",
                                   "Blank to shoot empty bullet at start\nArguments are 0-indexed")
        if (index.isPresent) {
            val string = index.get()
            if (string.isBlank()) {
                InNode(null)
            }
            else {
                InNode(string.toInt())
            }
        }
        else {
            null
        }
    }

    override fun toJson(): JsonElement {
        val json = super.toJson().asJsonObject
        json.addProperty("index", index?.toString() ?: "null")
        return json
    }

    override val jsonFactory: (JsonObject) -> INode = { json ->
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

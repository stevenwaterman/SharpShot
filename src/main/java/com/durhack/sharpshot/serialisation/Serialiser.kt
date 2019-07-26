package com.durhack.sharpshot.serialisation

import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.Extract
import com.durhack.sharpshot.gui.util.CoordinateRange2D
import com.durhack.sharpshot.registry.NodeRegistry
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser

internal object Serialiser {
    fun save(extract: Extract): String {
        val nodes = JsonArray()
        extract.nodes.forEach { coordinate, node ->
            val nodeJson = JsonObject()
            nodeJson.add("coordinate", coordinate.toJson())
            nodeJson.add("node", NodeRegistry.toJson(node))
            nodes.add(nodeJson)
        }

        val containerJson = JsonObject()
        containerJson.addProperty("width", extract.width)
        containerJson.addProperty("height", extract.height)
        containerJson.add("nodes", nodes)

        val gson = GsonBuilder().create()
        return gson.toJson(containerJson)
    }

    fun load(jsonString: String): Extract {
        val json = JsonParser().parse(jsonString).asJsonObject

        val width = json["width"].asInt
        val height = json["height"].asInt

        val nodesJson = json["nodes"].asJsonArray
        val nodes = nodesJson.map { jsonElement ->
            val jsonObject = jsonElement.asJsonObject
            val coordinateJson = jsonObject["coordinate"].asJsonObject
            val nodeJson = jsonObject["node"].asJsonObject

            val coordinate = Coordinate.fromJson(coordinateJson)
            val node = NodeRegistry.create(nodeJson)
            coordinate to node
        }.toMap()

        return Extract(nodes, CoordinateRange2D(width, height))
    }
}

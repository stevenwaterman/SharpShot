package com.durhack.sharpshot.util

import com.durhack.sharpshot.Coordinate
import com.durhack.sharpshot.NodeRegistry
import com.durhack.sharpshot.gui.container.Container
import com.google.gson.*

internal object Serialiser {
    fun getJSON(container: Container): String {
        val nodes = JsonArray()
        container.nodes.forEach { coordinate, node ->
            val nodeJson = JsonObject()
            nodeJson.add("coordinate", coordinate.toJson())
            nodeJson.add("node", node.toJson())
            nodes.add(nodeJson)
        }

        val containerJson = JsonObject()
        containerJson.addProperty("width", container.width)
        containerJson.addProperty("height", container.height)
        containerJson.add("nodes", nodes)

        val gson = GsonBuilder().create()
        return gson.toJson(containerJson)
    }

    fun loadJSON(jsonString: String): Container {
        val json = JsonParser().parse(jsonString).asJsonObject

        val width = json["width"].asInt
        val height = json["height"].asInt
        val container = Container(width, height)

        val nodesJson = json["nodes"].asJsonArray
        nodesJson.forEach {jsonElement ->
            val jsonObject = jsonElement.asJsonObject
            val coordinateJson = jsonObject["coordinate"].asJsonObject
            val nodeJson = jsonObject["node"].asJsonObject

            val coordinate = Coordinate.fromJson(coordinateJson)
            val node = NodeRegistry.fromJson(nodeJson)
            container.nodes[coordinate] = node
        }
        return container
    }
}

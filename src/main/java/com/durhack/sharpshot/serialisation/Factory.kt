package com.durhack.sharpshot.serialisation

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.util.NodeRegistry
import com.google.gson.JsonObject

object Factory{
    private val factories = NodeRegistry.nodes.map { it.type to it.jsonFactory }.toMap()

    fun fromJson(json: JsonObject): AbstractNode {
        val type = json.get("type").asString
        val factory = factories[type] ?: throw IllegalArgumentException("Cannot find factory for type $type")
        return factory(json)
    }
}
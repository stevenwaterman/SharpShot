package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.logic.Direction
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import javafx.scene.Node
import java.math.BigInteger

abstract class INode {
    var rotation = Direction.UP

    fun rotate() {
        rotation = rotation.clockwise
    }

    abstract fun run(bullet: Bullet): Map<Direction, BigInteger?>
    abstract fun graphic(): Node
    abstract fun reset()

    abstract val tooltip: String
    open val factory: () -> INode? = this::class.java::newInstance

    abstract val type: String

    final override fun toString() = type.split(" ").joinToString(" ") {
        it.first().toUpperCase() + it.drop(1).toLowerCase()
    }

    open fun toJson(): JsonElement {
        val json = JsonObject()
        json.addProperty("type", type)
        json.addProperty("rotation", rotation.quarters)
        return json
    }

    open val jsonFactory: (JsonObject) -> INode = { json ->
        val node = this::class.java.newInstance()
        val rotation = json["rotation"].asInt
        node.rotation = Direction.ofQuarters(rotation)
        node
    }
}
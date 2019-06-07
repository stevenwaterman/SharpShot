package com.durhack.sharpshot.core.nodes

import com.durhack.sharpshot.core.state.Bullet
import com.durhack.sharpshot.core.state.Direction
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import javafx.scene.Node
import java.math.BigInteger

abstract class INode {
    var direction = Direction.UP

    fun process(bullet: Bullet): Map<Direction, BigInteger?> {
        val absDir = bullet.direction
        val relDir = absDir - direction
        return process(relDir, bullet.value)
    }

    protected abstract fun process(relativeDirection: Direction, value: BigInteger?): Map<Direction, BigInteger?>
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
        json.addProperty("direction", direction.quarters)
        return json
    }

    open val jsonFactory: (JsonObject) -> INode = { json ->
        val node = this::class.java.newInstance()
        val rotation = json["direction"].asInt
        node.direction = Direction.ofQuarters(rotation)
        node
    }
}
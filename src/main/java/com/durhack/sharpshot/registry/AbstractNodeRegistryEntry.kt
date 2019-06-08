package com.durhack.sharpshot.registry

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import com.google.gson.JsonObject
import javafx.scene.canvas.GraphicsContext

abstract class AbstractNodeRegistryEntry<T : AbstractNode>(val example: T, val name: String, val tooltip: String) {

    val type: String = example.type

    @Suppress("UNCHECKED_CAST")
    fun unsafeCast(node: AbstractNode): T = node as T

    fun unsafeToJson(node: AbstractNode): JsonObject{
        val castNode = unsafeCast(node)
        return toJson(castNode)
    }

    open fun toJson(node: T): JsonObject {
        val json = JsonObject()
        json.addProperty("type", node.type)
        json.addProperty("direction", node.direction.quarters)
        return json
    }

    open fun create(json: JsonObject): T{
        val quarters = json["direction"].asInt
        val direction = Direction.ofQuarters(quarters)

        val node = example::class.java.getConstructor(Direction::class.java).newInstance(direction)
        val rotation = json["direction"].asInt
        node.direction = Direction.ofQuarters(rotation)
        return node
    }

    fun unsafeDraw(node: AbstractNode,
                   gc: GraphicsContext,
                   x: Double,
                   y: Double,
                   scale: Double){
        draw(unsafeCast(node), gc, x, y, scale)
    }

    abstract fun draw(node: T,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Double)
}
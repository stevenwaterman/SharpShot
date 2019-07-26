package com.durhack.sharpshot.registry

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Direction
import com.google.gson.JsonObject
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext

abstract class RegistryEntry<T : AbstractNode>(val example: T, val name: String, val description: String) {

    val type: String = example.type

    @Suppress("UNCHECKED_CAST")
    fun unsafeCast(node: AbstractNode): T = node as T

    fun unsafeToJson(node: AbstractNode): JsonObject {
        val castNode = unsafeCast(node)
        return toJson(castNode)
    }

    open fun toJson(node: T): JsonObject {
        val json = JsonObject()
        json.addProperty("type", node.type)
        json.addProperty("direction", node.direction.quarters)
        return json
    }

    open fun create(json: JsonObject): T {
        val quarters = json["direction"].asInt
        val direction = Direction.ofQuarters(quarters)
        return example::class.java.getConstructor(Direction::class.java).newInstance(direction)
    }

    fun unsafeDraw(node: AbstractNode,
                   gc: GraphicsContext,
                   x: Double,
                   y: Double,
                   scale: Int) {
        draw(unsafeCast(node), gc, x, y, scale)
    }

    abstract fun draw(node: T,
                      gc: GraphicsContext,
                      x: Double,
                      y: Double,
                      scale: Int)

    fun getGraphic(scale: Int) = Canvas(scale.toDouble(), scale.toDouble()).apply {
        draw(example, graphicsContext2D, 0.0, 0.0, scale)
    }

    override fun toString() = "RegistryEntry(name='$name')"

    fun createNode(): T {
        return example::class.java.getConstructor(Direction::class.java).newInstance(Direction.UP)
    }
}
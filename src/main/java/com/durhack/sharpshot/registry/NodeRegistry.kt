package com.durhack.sharpshot.registry

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.registry.entries.*
import com.google.gson.JsonObject
import javafx.scene.canvas.GraphicsContext

object NodeRegistry {
    private val entries: Map<String, AbstractNodeRegistryEntry<out AbstractNode>> =
            listOf(
                    InNodeEntry(),
                    ListNodeEntry(),
                    StackNodeEntry(),
                    ConstantNodeEntry(),
                    RandomNodeEntry(),
                    HaltNodeEntry(),
                    SplitterNodeEntry(),
                    BranchNodeEntry(),
                    IfPositiveNodeEntry(),
                    IfZeroNodeEntry(),
                    IfNullNodeEntry(),
                    VoidNodeEntry(),
                    AddNodeEntry(),
                    DivNodeEntry(),
                    MultNodeEntry(),
                    SubNodeEntry()
                  ).associateBy(AbstractNodeRegistryEntry<out AbstractNode>::type)

    private fun getEntry(type: String) =
            entries[type] ?: throw IllegalArgumentException("Unrecognised node type $type")

    private fun getEntry(node: AbstractNode) = getEntry(node.type)

    fun create(json: JsonObject) = getEntry(json["type"].asString).create(json)
    fun toJson(node: AbstractNode): JsonObject = getEntry(node).unsafeToJson(node)
    fun draw(node: AbstractNode, gc: GraphicsContext, x: Double, y: Double, scale: Double) {
        getEntry(node.type).unsafeDraw(node, gc, x, y, scale)
    }
}
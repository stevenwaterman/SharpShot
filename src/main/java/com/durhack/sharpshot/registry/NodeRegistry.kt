package com.durhack.sharpshot.registry

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.registry.entries.*
import com.google.gson.JsonObject
import javafx.scene.canvas.GraphicsContext

object NodeRegistry {
    val inNodeEntry = InNodeEntry()
    val listNodeEntry = ListNodeEntry()
    val stackNodeEntry = StackNodeEntry()
    val splitterNodeEntry = SplitterNodeEntry()
    val constantNodeEntry = ConstantNodeEntry()
    val randomNodeEntry = RandomNodeEntry()
    val haltNodeEntry = HaltNodeEntry()
    val branchNodeEntry = BranchNodeEntry()
    val ifPositiveNodeEntry = IfPositiveNodeEntry()
    val ifZeroNodeEntry = IfZeroNodeEntry()
    val ifNullNodeEntry = IfNullNodeEntry()
    val voidNodeEntry = VoidNodeEntry()
    val addNodeEntry = AddNodeEntry()
    val divNodeEntry = DivNodeEntry()
    val multNodeEntry = MultNodeEntry()
    val subNodeEntry = SubNodeEntry()

    val entries = listOf(
            inNodeEntry,
            listNodeEntry,
            voidNodeEntry,
            haltNodeEntry,
            splitterNodeEntry,
            stackNodeEntry,
            constantNodeEntry,
            randomNodeEntry,
            branchNodeEntry,
            ifPositiveNodeEntry,
            ifZeroNodeEntry,
            ifNullNodeEntry,
            addNodeEntry,
            subNodeEntry,
            multNodeEntry,
            divNodeEntry
                        )

    private val mapping: Map<String, RegistryEntry<out AbstractNode>> =
            entries.associateBy(RegistryEntry<out AbstractNode>::type)

    private fun getEntry(type: String) =
            mapping[type] ?: throw IllegalArgumentException("Unrecognised node type $type")

    private fun getEntry(node: AbstractNode) = getEntry(node.type)

    fun create(json: JsonObject) = getEntry(json["type"].asString).create(json)
    fun toJson(node: AbstractNode): JsonObject = getEntry(node).unsafeToJson(node)
    fun draw(node: AbstractNode, gc: GraphicsContext, x: Double, y: Double, scale: Int) {
        getEntry(node.type).unsafeDraw(node, gc, x, y, scale)
    }
}
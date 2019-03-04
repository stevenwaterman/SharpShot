package com.durhack.sharpshot.nodes

import com.durhack.sharpshot.gui.NodeCreatorElement
import com.durhack.sharpshot.nodes.input.InNode
import com.durhack.sharpshot.nodes.input.ListNode
import com.durhack.sharpshot.nodes.math.AddNode
import com.durhack.sharpshot.nodes.math.DivNode
import com.durhack.sharpshot.nodes.math.MultNode
import com.durhack.sharpshot.nodes.math.SubNode
import com.durhack.sharpshot.nodes.other.ConstantNode
import com.durhack.sharpshot.nodes.other.HaltNode
import com.durhack.sharpshot.nodes.other.RandomNode
import com.durhack.sharpshot.nodes.other.StackNode
import com.durhack.sharpshot.nodes.output.PutIntNode
import com.durhack.sharpshot.nodes.routing.*
import com.google.gson.JsonObject
import java.math.BigInteger

object NodeRegistry {
    val nodes: List<INode> = listOf(
            InNode(1),
            ListNode(),
            StackNode(),
            PutIntNode(),
            ConstantNode(BigInteger.ONE),
            RandomNode(),
            HaltNode(),
            SplitterNode(),
            BranchNode(),
            IfPositiveNode(),
            IfZeroNode(),
            IfNullNode(),
            VoidNode(),
            AddNode(),
            DivNode(),
            MultNode(),
            SubNode()
                                   )

    private val factories = nodes.map { it.type to it.jsonFactory }.toMap()
    val nodeCreatorElements = nodes.map { NodeCreatorElement(it) }

    fun fromJson(json: JsonObject): INode {
        val type = json.get("type").asString
        val factory = factories[type] ?: throw IllegalArgumentException("Cannot find factory for type $type")
        return factory(json)
    }
}
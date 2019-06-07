package com.durhack.sharpshot.core.nodes

import com.durhack.sharpshot.core.nodes.input.InNode
import com.durhack.sharpshot.core.nodes.input.ListNode
import com.durhack.sharpshot.core.nodes.math.AddNode
import com.durhack.sharpshot.core.nodes.math.DivNode
import com.durhack.sharpshot.core.nodes.math.MultNode
import com.durhack.sharpshot.core.nodes.math.SubNode
import com.durhack.sharpshot.core.nodes.other.ConstantNode
import com.durhack.sharpshot.core.nodes.other.HaltNode
import com.durhack.sharpshot.core.nodes.other.RandomNode
import com.durhack.sharpshot.core.nodes.other.StackNode
import com.durhack.sharpshot.core.nodes.routing.BranchNode
import com.durhack.sharpshot.core.nodes.routing.SplitterNode
import com.durhack.sharpshot.core.nodes.routing.VoidNode
import com.durhack.sharpshot.core.nodes.routing.conditional.IfNullNode
import com.durhack.sharpshot.core.nodes.routing.conditional.IfPositiveNode
import com.durhack.sharpshot.core.nodes.routing.conditional.IfZeroNode
import com.durhack.sharpshot.gui.NodeCreatorElement
import com.google.gson.JsonObject
import java.math.BigInteger

object NodeRegistry {
    val nodes: List<INode> = listOf(
            InNode(1),
            ListNode(),
            StackNode(),
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
    val nodeCreatorElements = nodes.map(::NodeCreatorElement)

    fun fromJson(json: JsonObject): INode {
        val type = json.get("type").asString
        val factory = factories[type] ?: throw IllegalArgumentException("Cannot find factory for type $type")
        return factory(json)
    }
}
package com.durhack.sharpshot

import com.durhack.sharpshot.gui.NodeCreatorElement
import com.durhack.sharpshot.nodes.*
import com.durhack.sharpshot.nodes.io.AsciiNode
import com.durhack.sharpshot.nodes.io.InNode
import com.durhack.sharpshot.nodes.io.ListNode
import com.durhack.sharpshot.nodes.io.OutNode
import com.durhack.sharpshot.nodes.math.AddNode
import com.durhack.sharpshot.nodes.math.DivNode
import com.durhack.sharpshot.nodes.math.MultNode
import com.durhack.sharpshot.nodes.math.SubNode
import com.durhack.sharpshot.nodes.routing.*
import com.google.gson.JsonObject
import java.math.BigInteger

object NodeRegistry {
    val nodes: List<INode> = listOf(
            AsciiNode(),
            InNode(1),
            ListNode(),
            OutNode(),
            AddNode(),
            DivNode(),
            MultNode(),
            SubNode(),
            RotateNode(),
            ACRotateNode(),
            BranchNode(),
            IfPositiveNode(),
            IfZeroNode(),
            SplitterNode(),
            VoidNode(),
            ConstantNode(BigInteger.ONE),
            HaltNode(),
            RandomNode(),
            StackNode()
                                   )

    private val factories = nodes.map { it.type to it.jsonFactory }.toMap()
    val nodeCreatorElements = nodes.map { NodeCreatorElement(it) }

    fun fromJson(json: JsonObject): INode {
        val type = json.get("type").asString
        val factory = factories[type] ?: throw IllegalArgumentException("Cannot find factory for type $type")
        return factory(json)
    }
}
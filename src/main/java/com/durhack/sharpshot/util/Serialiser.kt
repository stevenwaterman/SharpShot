package com.durhack.sharpshot.util

import com.durhack.sharpshot.Coordinate
import com.durhack.sharpshot.Direction
import com.durhack.sharpshot.INode
import com.durhack.sharpshot.nodes.ConstantNode
import com.durhack.sharpshot.nodes.Container
import com.durhack.sharpshot.nodes.HaltNode
import com.durhack.sharpshot.nodes.RandomNode
import com.durhack.sharpshot.nodes.StackNode
import com.durhack.sharpshot.nodes.io.AsciiNode
import com.durhack.sharpshot.nodes.io.InNode
import com.durhack.sharpshot.nodes.io.ListNode
import com.durhack.sharpshot.nodes.io.OutNode
import com.durhack.sharpshot.nodes.math.AddNode
import com.durhack.sharpshot.nodes.math.DivNode
import com.durhack.sharpshot.nodes.math.MultNode
import com.durhack.sharpshot.nodes.math.SubNode
import com.durhack.sharpshot.nodes.routing.ACRotateNode
import com.durhack.sharpshot.nodes.routing.BranchNode
import com.durhack.sharpshot.nodes.routing.IfPositiveNode
import com.durhack.sharpshot.nodes.routing.IfZeroNode
import com.durhack.sharpshot.nodes.routing.RotateNode
import com.durhack.sharpshot.nodes.routing.SplitterNode
import com.durhack.sharpshot.nodes.routing.VoidNode
import com.google.gson.GsonBuilder
import java.math.BigInteger
import java.util.*

internal object Serialiser {

    internal class NodeData(val type: String, val x: Int, val y: Int, val rotation: Direction, var extra: String)

    internal class Data {
        var width: Int = 0
        var height: Int = 0
        val nodes: MutableList<NodeData> = ArrayList()
    }

    private val nodeFactories = mapOf<String, (NodeData) -> INode>(
            "InNode" to { node -> InNode(Integer.parseInt(node.extra)) },
            "OutNode" to { _ -> OutNode() },
            "AsciiNode" to { _ -> AsciiNode() },
            "AddNode" to { _ -> AddNode() },
            "SubNode" to { _ -> SubNode() },
            "MultNode" to { _ -> MultNode() },
            "DivNode" to { _ -> DivNode() },
            "BranchNode" to { _ -> BranchNode() },
            "SplitterNode" to { _ -> SplitterNode() },
            "ConstantNode" to { node -> ConstantNode(BigInteger(node.extra)) },
            "VoidNode" to { _ -> VoidNode() },
            "IfPositiveNode" to { _ -> IfPositiveNode() },
            "IfZeroNode" to { _ -> IfZeroNode() },
            "RotateNode" to { _ -> RotateNode() },
            "ACRotateNode" to { _ -> ACRotateNode() },
            "RandomNode" to { _ -> RandomNode() },
            "HaltNode" to { _ -> HaltNode() },
            "ListNode" to { _ -> ListNode() },
            "StackNode" to { _ -> StackNode() })

    fun getJSON(cont: Container): String {
        val data = Data()

        data.width = cont.width
        data.height = cont.height

        for ((coord, node) in cont.nodes) {

            val nd = NodeData(node.javaClass.simpleName, coord.x, coord.y, node.rotation, "")

            when (node) {
                is ConstantNode -> nd.extra = node.value!!.toString()
                is InNode       -> nd.extra = node.index.toString()
            }

            data.nodes.add(nd)
        }


        val gson = GsonBuilder().create()
        gson.toJson(data)
        return gson.toJson(data)
    }

    fun loadJSON(json: String): Container {
        val gson = GsonBuilder().create()
        val data = gson.fromJson<Data>(json, Data::class.java)

        val container = Container(data.width, data.height)

        data.nodes.forEach { jsonNode ->
            val newNode = nodeFactories[jsonNode.type]?.invoke(jsonNode)
            if (newNode != null) {
                newNode.rotation = jsonNode.rotation
                container.nodes[Coordinate(jsonNode.x, jsonNode.y)] = newNode
            }
        }

        return container
    }
}

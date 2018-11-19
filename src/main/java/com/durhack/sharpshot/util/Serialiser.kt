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

        for (nd in data.nodes) {
            val type = nd.type
            val newNode: INode
            when (type) {
                "InNode"         -> newNode = InNode(Integer.parseInt(nd.extra))
                "OutNode"        -> newNode = OutNode()
                "AsciiNode"      -> newNode = AsciiNode()

                "AddNode"        -> newNode = AddNode()
                "SubNode"        -> newNode = SubNode()
                "MultNode"       -> newNode = MultNode()
                "DivNode"        -> newNode = DivNode()

                "BranchNode"     -> newNode = BranchNode()
                "SplitterNode"   -> newNode = SplitterNode()

                "ConstantNode"   -> newNode = ConstantNode(BigInteger(nd.extra))

                "VoidNode"       -> newNode = VoidNode()

                "IfPositiveNode" -> newNode = IfPositiveNode()
                "IfZeroNode"     -> newNode = IfZeroNode()

                "RotateNode"     -> newNode = RotateNode()
                "ACRotateNode"   -> newNode = ACRotateNode()

                "RandomNode"     -> newNode = RandomNode()

                "HaltNode"       -> newNode = HaltNode()
                "ListNode"       -> newNode = ListNode()
                "StackNode"      -> newNode = StackNode()

                else             -> {
                    throw RuntimeException("This shouldn't happen, cannot read serialised node in Serialiser: $type")
                }
            }

            // wow.. lazy
            while (newNode.rotation != nd.rotation) newNode.rotateClockwise()

            container.nodes[Coordinate(nd.x, nd.y)] = newNode
        }

        return container
    }
}

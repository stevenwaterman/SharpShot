package com.durhack.sharpshot.gui

import com.durhack.sharpshot.INode
import com.durhack.sharpshot.nodes.ConstantNode
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
import javafx.collections.FXCollections
import javafx.scene.control.ListView
import javafx.scene.control.TextInputDialog
import java.math.BigInteger

internal class NodeCreator(getDefaultInNodeIndex: () -> Int) : ListView<NodeTypeDescriptor<*>>(FXCollections.observableArrayList()) {
    init {
        items.addAll(listOf(NodeTypeDescriptor(InNode(null),
                                               "Provides Input at program start and every time a bullet passes through") {
            val index = getNumberInput("Enter Input Index",
                                       "Blank to shoot empty bullet at start\nArguments are 0-indexed",
                                       getDefaultInNodeIndex())?.toInt() ?: return@NodeTypeDescriptor null
            return@NodeTypeDescriptor InNode(index)
        },
                            NodeTypeDescriptor(OutNode(), "Consumes and prints bullets") { OutNode() },
                            NodeTypeDescriptor(AsciiNode(),
                                               "Consumes and prints bullets based on their ASCII code equivalent") { AsciiNode() },
                            NodeTypeDescriptor(AddNode(), "Adds two bullets") { AddNode() },
                            NodeTypeDescriptor(SubNode(), "Subtracts the second bullet from the first") { SubNode() },
                            NodeTypeDescriptor(AddNode(), "Adds two bullets") { AddNode() },
                            NodeTypeDescriptor(MultNode(), "Multiplies two numbers") { MultNode() },
                            NodeTypeDescriptor(DivNode(), "Divides the first bullet by the second") { DivNode() },
                            NodeTypeDescriptor(BranchNode(), "Redirects a bullet") { BranchNode() },
                            NodeTypeDescriptor(IfPositiveNode(),
                                               "Redirects all positive bullets (>0). Other bullets pass through unaffected") { IfPositiveNode() },
                            NodeTypeDescriptor(IfZeroNode(),
                                               "Redirects all zero bullets (=0). Other bullets pass through unaffected") { DivNode() },
                            NodeTypeDescriptor(SplitterNode(),
                                               "A bullet in one side produces 3 bullets in the others") { SplitterNode() },
                            NodeTypeDescriptor(ConstantNode(BigInteger.ZERO),
                                               "Whenever a bullet passes through, release another bullet with pre-set value") {
                                val value = getNumberInput("Enter Constant Value",
                                                           "Leave empty for empty bullets",
                                                           getDefaultInNodeIndex())
                                return@NodeTypeDescriptor ConstantNode(value)
                            },
                            NodeTypeDescriptor(RandomNode(),
                                               "Provides a random output from 0 (inclusive) to input bullet value (exclusive)") { SplitterNode() },
                            NodeTypeDescriptor(RotateNode(), "Rotates incoming bullets clockwise") { RotateNode() },
                            NodeTypeDescriptor(ACRotateNode(),
                                               "Rotates incoming bullets anticlockwise") { ACRotateNode() },
                            NodeTypeDescriptor(VoidNode(), "Destroys all incoming bullets") { VoidNode() },
                            NodeTypeDescriptor(HaltNode(), "Terminates the Program") { HaltNode() },
                            NodeTypeDescriptor(ListNode(),
                                               "Every time a bullet comes in, outputs the next value in the list of inputs") { ListNode() },
                            NodeTypeDescriptor(StackNode(),
                                               "Inputs in the back pop from the stack, inputs to other sides get added to the stack") { StackNode() }))
    }

    private fun getNumberInput(header: String, content: String = "", start: Int = 0): BigInteger? {
        val dialog = TextInputDialog(start.toString())
        dialog.title = "New Node"
        dialog.headerText = header
        dialog.contentText = content

        val result = dialog.showAndWait()
        return when {
            result.isPresent -> BigInteger(result.get())
            else             -> null
        }
    }

    fun createNode(): INode? {
        val descriptor = selectionModel.selectedItem
        return descriptor?.create()
    }
}

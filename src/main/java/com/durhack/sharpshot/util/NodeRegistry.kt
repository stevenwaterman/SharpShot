package com.durhack.sharpshot.util

import com.durhack.sharpshot.core.nodes.AbstractNode
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
import java.math.BigInteger

object NodeRegistry {
    val nodes: List<AbstractNode> = listOf(
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
}
package com.durhack.sharpshot.gui.container.menus.nodecreator.nodeforms

import com.durhack.sharpshot.core.nodes.other.ConstantNode
import com.durhack.sharpshot.core.state.Direction

class ConstantNodeForm(close: () -> Unit, success: (ConstantNode) -> Unit) :
        IntNodeForm<ConstantNode>(close,
                            success,
                            "What value should this node output?",
                            "Press empty to fire blank bullets.",
                            { ConstantNode(it, Direction.UP) }
                           )
package com.durhack.sharpshot.gui.container.menus.nodecreator.nodeforms

import com.durhack.sharpshot.core.nodes.input.InNode
import com.durhack.sharpshot.core.state.Direction

class InNodeForm(close: () -> Unit, success: (InNode) -> Unit) :
        IntNodeForm<InNode>(close,
                            success,
                            "Which of the inputs should this node output?",
                            "The first index is number 1. Press empty to fire blank bullets.",
                            { InNode(it?.toInt(), Direction.UP) }
                           )
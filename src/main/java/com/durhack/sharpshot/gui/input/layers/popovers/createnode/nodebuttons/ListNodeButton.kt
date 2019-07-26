package com.durhack.sharpshot.gui.input.layers.popovers.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.input.ListNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.NodeRegistry

class ListNodeButton(
        onHover: (AbstractNodeButton) -> Unit,
        showForm: (AbstractNodeForm) -> Unit,
        nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeButton(NodeRegistry.listNodeEntry, onHover, showForm, nodeCreated) {

    override val nodeForm = null
    override fun createNode() = ListNode(Direction.UP)
}
package com.durhack.sharpshot.gui.container.menus.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.other.StackNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.NodeRegistry

class StackNodeButton(
        onHover: (AbstractNodeButton) -> Unit,
        showForm: (AbstractNodeForm) -> Unit,
        nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeButton(NodeRegistry.stackNodeEntry, onHover, showForm, nodeCreated) {

    override val nodeForm = null
    override fun createNode() = StackNode(Direction.UP)
}
package com.durhack.sharpshot.gui.container.input.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.other.HaltNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.NodeRegistry

class HaltNodeButton(
        onHover: (AbstractNodeButton) -> Unit,
        showForm: (AbstractNodeForm) -> Unit,
        nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeButton(NodeRegistry.haltNodeEntry, onHover, showForm, nodeCreated) {

    override val nodeForm = null
    override fun createNode() = HaltNode(Direction.UP)
}
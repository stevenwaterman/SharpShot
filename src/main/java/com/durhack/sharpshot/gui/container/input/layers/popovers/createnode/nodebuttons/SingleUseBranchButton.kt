package com.durhack.sharpshot.gui.container.input.layers.popovers.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.routing.SingleUseBranchNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.NodeRegistry

class SingleUseBranchButton(
        onHover: (AbstractNodeButton) -> Unit,
        showForm: (AbstractNodeForm) -> Unit,
        nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeButton(NodeRegistry.singleUseBranchNodeEntry, onHover, showForm, nodeCreated) {

    override val nodeForm = null
    override fun createNode() = SingleUseBranchNode(Direction.UP)
}
package com.durhack.sharpshot.gui.input.layers.popovers.createnode.nodebuttons

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.nodes.routing.VoidNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.registry.NodeRegistry

class VoidNodeButton(
        onHover: (AbstractNodeButton) -> Unit,
        showForm: (AbstractNodeForm) -> Unit,
        nodeCreated: (AbstractNode) -> Unit) :
        AbstractNodeButton(NodeRegistry.voidNodeEntry, onHover, showForm, nodeCreated) {

    override val nodeForm = null
    override fun createNode() = VoidNode(Direction.UP)
}
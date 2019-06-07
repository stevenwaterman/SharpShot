package com.durhack.sharpshot.gui

import com.durhack.sharpshot.core.nodes.input.InNode
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerController
import com.durhack.sharpshot.gui.container.CenteredScrollPane
import com.durhack.sharpshot.util.container
import tornadofx.*

class MainView(): View(){
    private val containerController: ContainerController by inject()

    override val root = borderpane {
        center{
            val containerView = containerController.view
            val containerScrollPane = CenteredScrollPane(containerView)
            add(containerScrollPane)

            //TODO remove
            val inNode = InNode(5)
            container.nodes[Coordinate(1, 1)] = inNode
            containerView.render()
        }
    }
}
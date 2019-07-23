package com.durhack.sharpshot.gui.container.input.createnode

import com.durhack.sharpshot.gui.util.coord
import com.durhack.sharpshot.gui.util.position
import com.durhack.sharpshot.util.container
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Priority
import tornadofx.*

class CreateNodeClickLayer : View() {
    private val nodeCreatorMenu: CreateNodeMenu by inject()

    override val root = pane {
        id = "Create Node Click Layer"
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS

        visibleWhen(nodeCreatorMenu.root.visibleProperty())
        add(nodeCreatorMenu)
    }

    fun handle(event: MouseEvent) {
        val coord = event.coord ?: return
        if (container.nodes[coord] == null) {
            event.consume()
            nodeCreatorMenu.show(coord, event.position)
        }
    }
}
package com.durhack.sharpshot.gui.container.input.layers

import com.durhack.sharpshot.gui.container.input.createnode.CreateNodeMenu
import com.durhack.sharpshot.gui.util.addClickHandler
import com.durhack.sharpshot.gui.util.coord
import com.durhack.sharpshot.gui.util.position
import com.durhack.sharpshot.util.container
import javafx.scene.input.MouseButton
import javafx.scene.layout.Priority
import tornadofx.*

class CreateNodeClickLayer : View() {
    private val nodeCreatorMenu: CreateNodeMenu by inject()

    override val root = pane {
        id = "Create Node Click Layer"
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS

        add(nodeCreatorMenu)

        addClickHandler { event ->
            if (event.button == MouseButton.PRIMARY) {
                val coord = event.coord ?: return@addClickHandler
                if (container.nodes[coord] == null) {
                    event.consume()
                    nodeCreatorMenu.show(coord, event.position)
                    event.consume()
                }
            }
        }
    }
}
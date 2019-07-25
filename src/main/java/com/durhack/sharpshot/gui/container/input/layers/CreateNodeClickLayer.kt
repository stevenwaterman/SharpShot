package com.durhack.sharpshot.gui.container.input.layers

import com.durhack.sharpshot.gui.container.input.layers.popovers.CreateNodeMenu
import com.durhack.sharpshot.gui.util.addClickHandler
import com.durhack.sharpshot.gui.util.coord
import com.durhack.sharpshot.gui.util.point
import com.durhack.sharpshot.util.container
import javafx.scene.input.MouseButton
import javafx.scene.layout.Priority
import tornadofx.*

class CreateNodeClickLayer : View() {
    private val boardSelector: BoardSelector by inject()
    private val nodeCreatorMenu: CreateNodeMenu by inject()

    override val root = stackpane {
        id = "Create Node Click Layer"
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS

        addClickHandler { event ->
            if (event.button == MouseButton.PRIMARY) {
                if (!boardSelector.dragging) {
                    val coord = event.coord ?: return@addClickHandler
                    if (container.nodes[coord] == null) {
                        event.consume()
                        nodeCreatorMenu.show(coord, event.point)
                    }
                }
            }
        }
    }
}
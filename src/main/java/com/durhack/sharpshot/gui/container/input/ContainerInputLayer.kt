package com.durhack.sharpshot.gui.container.input

import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.input.createnode.CreateNodeClickLayer
import com.durhack.sharpshot.gui.container.input.editboard.BoardSelector
import com.durhack.sharpshot.gui.container.input.editboard.SingleNodeEdit
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import tornadofx.*

class ContainerInputLayer : View() {
    private val boardSelector: BoardSelector by inject()
    private val singleNodeEdit: SingleNodeEdit by inject()
    private val createNodeClickLayer: CreateNodeClickLayer by inject()

    private val containerView: ContainerView by inject()
    private var mouseX = 0.0
    private var mouseY = 0.0
    val hoverCoord: Coordinate? get() = containerView.getCoord(mouseX, mouseY)

    override val root = stackpane {
        id = "Container Input Layer"

        add(boardSelector)
        add(singleNodeEdit)
        add(createNodeClickLayer)

        // Prevent mouse buttons other than right click from panning the scroll pane
        addEventHandler(MouseEvent.MOUSE_PRESSED) {
            if (it.button != MouseButton.SECONDARY) {
                it.consume()
            }
        }

        setOnMouseMoved {
            mouseX = it.x
            mouseY = it.y
        }

        addEventHandler(KeyEvent.KEY_PRESSED) {
            when {
                it.code == KeyCode.Q -> singleNodeEdit.rotateHoveredACW()
                it.code == KeyCode.E -> singleNodeEdit.rotateHoveredCW()
                it.code in listOf(KeyCode.DELETE, KeyCode.BACK_SPACE, KeyCode.R) -> singleNodeEdit.deleteHovered()
            }
        }
    }
}
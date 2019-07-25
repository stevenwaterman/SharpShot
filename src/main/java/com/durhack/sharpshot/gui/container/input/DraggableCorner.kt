package com.durhack.sharpshot.gui.container.input

import com.durhack.sharpshot.core.control.ContainerModifier
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.gui.util.makeDraggable
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import tornadofx.*

class DraggableCorner(val vertical: Direction, val horizontal: Direction) : Fragment() {
    private val containerView: ContainerView by inject()

    override val root = hbox {
        id = "Draggable Corner"

        canvas(25.0, 25.0) {
            Draw.rightAngleTriangle(graphicsContext2D, vertical, 0.0, 0.0, 20, Color.BLACK)
            makeDraggable(ContainerView.innerScaleProp.divide(2)) { _, _, direction ->
                if (direction == vertical || direction == horizontal) {
                    ContainerModifier.increaseSize(direction)
                    containerView.render()
                    true
                }
                else {
                    if (ContainerModifier.canDecreaseSize(direction)) {
                        ContainerModifier.decreaseSize(direction)
                        containerView.render()
                        true
                    }
                    else {
                        false
                    }
                }
            }
        }

        addEventHandler(MouseEvent.MOUSE_DRAGGED) {
            if (it.button != MouseButton.SECONDARY) {
                it.consume()
            }
        }
    }
}
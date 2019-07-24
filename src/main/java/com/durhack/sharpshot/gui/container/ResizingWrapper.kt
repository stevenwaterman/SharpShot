package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.core.control.canDecreaseSize
import com.durhack.sharpshot.core.control.decreaseSize
import com.durhack.sharpshot.core.control.increaseSize
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.input.layers.ContainerInputLayer
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.gui.util.makeDraggable
import com.durhack.sharpshot.util.container
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import tornadofx.*

private class DraggableCorner(val vertical: Direction, val horizontal: Direction) : Fragment() {
    private val containerView: ContainerView by inject()

    override val root = hbox {
        id = "Draggable Corner"

        canvas(25.0, 25.0) {
            Draw.rightAngleTriangle(graphicsContext2D, vertical, 0.0, 0.0, 20, Color.BLACK)
            makeDraggable(ContainerView.innerScaleProp.divide(2)) { _, _, direction ->
                if (direction == vertical || direction == horizontal) {
                    container.increaseSize(direction)
                    containerView.render()
                    true
                }
                else {
                    if (container.canDecreaseSize(direction)) {
                        container.decreaseSize(direction)
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

class ResizingWrapper : View() {
    private val paddingAmnt = 50
    private val topArrow = DraggableCorner(Direction.UP, Direction.LEFT)
    private val bottomArrow = DraggableCorner(Direction.DOWN, Direction.RIGHT)
    private val containerView: ContainerView by inject()
    private val containerInputLayer: ContainerInputLayer by inject()

    override val root = gridpane {
        id = "Resizing Wrapper"

        enableWhen(container.runningProp.booleanBinding { it?.not() ?: true })
        paddingAll = paddingAmnt
        add(topArrow.root, 0, 0)
        add(bottomArrow.root, 2, 2)
        add(containerView.root, 1, 1)
        add(containerInputLayer.root, 1, 1)

        maxWidthProperty().bind(topArrow.root.maxWidthProperty() + containerView.root.maxWidthProperty() + bottomArrow.root.maxWidthProperty() + paddingAmnt)
        maxHeightProperty().bind(topArrow.root.maxHeightProperty() + containerView.root.maxHeightProperty() + bottomArrow.root.maxHeightProperty() + paddingAmnt)
    }
}
package com.durhack.sharpshot.gui.input.layers

import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.input.DraggableCorner
import com.durhack.sharpshot.util.globalContainer
import tornadofx.*

class LayerContainer : View() {
    private val topArrow = DraggableCorner(Direction.UP, Direction.LEFT)
    private val bottomArrow = DraggableCorner(Direction.DOWN, Direction.RIGHT)

    val containerView: ContainerView by inject()
    val containerInputLayer: ContainerInputLayer by inject()
    val singleNodeEdit: SingleNodeEdit by inject()
    val boardSelector: BoardSelector by inject()
    val createNodeClickLayer: CreateNodeClickLayer by inject()
    val viewPopover: ViewPopover by inject()

    val layers = listOf(
            containerInputLayer,
            singleNodeEdit,
            boardSelector,
            createNodeClickLayer,
            viewPopover
                       )

    private val layeredPane = stackpane {
        id = "Layered Pane"

        add(layers.first())

        layers.forEachIndexed { index, layer ->
            val next = layers.getOrNull(index + 1) ?: return@forEachIndexed
            layer.add(next)
        }
    }

    override val root = gridpane {
        id = "Resizing Wrapper"
        val paddingAmnt = 50

        enableWhen(globalContainer.runningProp.booleanBinding { it?.not() ?: true })
        paddingAll = paddingAmnt
        add(topArrow.root, 0, 0)
        add(bottomArrow.root, 2, 2)
        add(containerView.root, 1, 1)
        add(layeredPane, 1, 1)

        maxWidthProperty().bind(topArrow.root.maxWidthProperty() + containerView.root.maxWidthProperty() + bottomArrow.root.maxWidthProperty() + paddingAmnt)
        maxHeightProperty().bind(topArrow.root.maxHeightProperty() + containerView.root.maxHeightProperty() + bottomArrow.root.maxHeightProperty() + paddingAmnt)
    }
}
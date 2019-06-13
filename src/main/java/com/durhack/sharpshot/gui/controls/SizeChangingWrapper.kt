package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.core.control.canDecreaseSize
import com.durhack.sharpshot.core.control.decreaseSize
import com.durhack.sharpshot.core.control.increaseSize
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.CenteredScrollPane
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.shapes.Draw
import com.durhack.sharpshot.util.container
import javafx.geometry.Pos
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*

class SizeChangingWrapper: View(){
    private val scrollpane: CenteredScrollPane by inject()
    private val upArrow = UpArrow()
    private val rightArrow = RightArrow()
    private val downArrow = DownArrow()
    private val leftArrow = LeftArrow()

    override val root = borderpane{
        border = Border(BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(1.0)))
        center {
            add(scrollpane)
        }
        top{
            add(upArrow)
            BorderPane.setAlignment(upArrow.root, Pos.CENTER)
        }
        right{
            add(rightArrow)
            BorderPane.setAlignment(rightArrow.root, Pos.CENTER)
        }
        bottom{
            add(downArrow)
            BorderPane.setAlignment(downArrow.root, Pos.CENTER)
        }
        left{
            add(leftArrow)
            BorderPane.setAlignment(leftArrow.root, Pos.CENTER)
        }
    }
}

private class UpArrow: Fragment(){
    private val containerView: ContainerView by inject()
    private val label = label { bind(container.heightProp) }
    private val graphic = canvas {
        width = 40.0
        height = 40.0
        Draw.doubleArrow(graphicsContext2D, Direction.UP, 0.0, 0.0, 40, Color.BLACK)

        var dragStart: Double? = null
        var consumedAmount = 0.0
        var canDecrease = true

        setOnMousePressed {
            dragStart = it.y
            consumedAmount = 0.0
        }

        setOnMouseDragged {
            val capStart = dragStart ?: return@setOnMouseDragged
            val dragEnd = it.y
            val delta = dragEnd - capStart
            var newDelta = delta - consumedAmount

            while(canDecrease && newDelta > 25){
                if(canDecrease){
                    if(container.canDecreaseSize(Direction.DOWN)) {
                        container.decreaseSize(Direction.DOWN)
                        containerView.render()
                        consumedAmount += 25
                        newDelta -= 25
                    }
                    else{
                        canDecrease = false
                    }

                }
            }
            while(newDelta < -25){
                container.increaseSize(Direction.UP)
                containerView.render()
                consumedAmount -= 25
                newDelta += 25
                canDecrease = true
            }
        }
    }

    override val root = hbox(8.0, Pos.CENTER) {
        paddingAll = 8.0
        add(graphic)
        add(label)
    }
}

private class RightArrow: Fragment(){
    private val label = label { bind(container.widthProp) }
    private val graphic = canvas {
        width = 40.0
        height = 40.0
        Draw.doubleArrow(graphicsContext2D, Direction.RIGHT, 0.0, 0.0, 40, Color.BLACK)
    }

    override val root = vbox(8.0, Pos.CENTER) {
        paddingAll = 8.0
        add(label)
        add(graphic)
    }
}

private class DownArrow: Fragment(){
    private val label = label { bind(container.heightProp) }
    private val graphic = canvas {
        width = 40.0
        height = 40.0
        Draw.doubleArrow(graphicsContext2D, Direction.DOWN, 0.0, 0.0, 40, Color.BLACK)
    }

    override val root = hbox(8.0, Pos.CENTER) {
        paddingAll = 8.0
        add(graphic)
        add(label)
    }
}

private class LeftArrow: Fragment(){
    private val label = label { bind(container.widthProp) }
    private val graphic = canvas {
        width = 40.0
        height = 40.0
        Draw.doubleArrow(graphicsContext2D, Direction.LEFT, 0.0, 0.0, 40, Color.BLACK)
    }

    override val root = vbox(8.0, Pos.CENTER) {
        paddingAll = 8.0
        add(label)
        add(graphic)
    }
}
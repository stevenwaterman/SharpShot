package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.core.control.canDecreaseSize
import com.durhack.sharpshot.core.control.decreaseSize
import com.durhack.sharpshot.core.control.increaseSize
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.util.BigIntSpinner
import com.durhack.sharpshot.util.container
import javafx.geometry.Pos
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.text.Font
import tornadofx.*

class SizeModifier : View() {
    private val directions = ToggleGroup()

    private val topLeft = DirectionButton("TL", directions, Direction.UP, Direction.LEFT).apply { isSelected = true }
    private val topRight = DirectionButton("TR", directions, Direction.UP, Direction.RIGHT)
    private val bottomLeft = DirectionButton("BL", directions, Direction.DOWN, Direction.LEFT)
    private val bottomRight = DirectionButton("BR", directions, Direction.DOWN, Direction.RIGHT)
    private val toggles = listOf(topLeft, topRight, bottomLeft, bottomRight)
    private val selectedToggle get() = toggles.first { it.isSelected }

    private val widthSpinner = BigIntSpinner(container.width.toBigInteger()) { setSize() }
    private val heightSpinner = BigIntSpinner(container.height.toBigInteger()) { setSize() }

    override val root = vbox(8.0) {
        paddingAll = 4.0
        alignment = Pos.CENTER
        label("Direction") {
            alignment = Pos.CENTER
            font = Font(18.0)
        }
        gridpane {
            alignment = Pos.CENTER
            vgap = 4.0
            hgap = 4.0
            add(topLeft, 0, 0)
            add(topRight, 1, 0)
            add(bottomLeft, 0, 1)
            add(bottomRight, 1, 1)
        }
        vbox {
            alignment = Pos.CENTER
            label("Desired Width")
            add(widthSpinner.root)
        }
        vbox {
            alignment = Pos.CENTER
            label("Desired Height")
            add(heightSpinner.root)
        }
        hbox(16.0) {
            alignment = Pos.CENTER
            button("Set") {
                action { setSize() }
            }
            button("Trim") {
                action { trim() }
            }
        }
    }

    private val containerView: ContainerView by inject()

    private fun trim() {
        setWidth(1, Direction.LEFT)
        setWidth(1, Direction.RIGHT)
        setHeight(1, Direction.UP)
        setHeight(1, Direction.DOWN)

        widthSpinner.value = container.width.toBigInteger()
        heightSpinner.value = container.height.toBigInteger()

        containerView.render()
    }

    private fun setSize() {
        val selection = selectedToggle

        val desiredWidth = widthSpinner.value?.toInt()
        setWidth(desiredWidth, selection.incH)

        val desiredHeight = heightSpinner.value?.toInt()
        setHeight(desiredHeight, selection.incV)

        containerView.render()
    }

    private fun setWidth(desired: Int?, incDirection: Direction) {
        desired ?: return
        while (container.width < desired) {
            container.increaseSize(incDirection)
        }
        while (container.width > desired) {
            if (!container.canDecreaseSize(incDirection.inverse)) return
            container.decreaseSize(incDirection.inverse)
        }
    }

    private fun setHeight(desired: Int?, incDirection: Direction) {
        desired ?: return
        while (container.height < desired) {
            container.increaseSize(incDirection)
        }
        while (container.height > desired) {
            if (!container.canDecreaseSize(incDirection.inverse)) return
            container.decreaseSize(incDirection.inverse)
        }
    }
}

class DirectionButton(text: String, group: ToggleGroup, val incV: Direction, val incH: Direction) : ToggleButton(text) {
    init { toggleGroup = group }
}
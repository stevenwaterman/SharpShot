package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.core.control.ContainerModifier
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.input.layers.popovers.SelectionBox
import com.durhack.sharpshot.gui.util.CoordinateRange2D
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class SelectionInfo : View() {
    private val extractInfo: ExtractInfo by inject()
    private val containerView: ContainerView by inject()

    private val selectionBox: SelectionBox by inject()
    private val selectionProp = selectionBox.selectionProp
    private val selection: CoordinateRange2D? get() = selectionProp.value
    private val isSelected = selectionProp.booleanBinding(selectionProp) {
        it != null
    }
    private val selectionWidth = selectionProp.integerBinding(selectionProp) {
        if (it == null) {
            0
        }
        else {
            val range = it.xRange
            range.endInclusive - range.start
        }
    }
    private val selectionHeight = selectionProp.integerBinding(selectionProp) {
        if (it == null) {
            0
        }
        else {
            val range = it.yRange
            range.endInclusive - range.start
        }
    }

    override val root = vbox(8.0, Pos.CENTER) {
        id = "Selection Info"

        label("Selection") {
            font = Font(18.0)
        }

        hbox(alignment = Pos.CENTER) {
            enableWhen(isSelected)
            label("Size: ")
            label {
                bind(selectionWidth)
            }
            label(" x ")
            label {
                bind(selectionHeight)
            }
        }

        button("Delete") {
            enableWhen(isSelected)
            action {
                ContainerModifier.clear(selection!!)
                containerView.render()
            }
        }

        button("Cut") {
            enableWhen(isSelected)
            action {
                val extract = ContainerModifier.cut(selection!!)
                extractInfo.extract = extract
                containerView.render()
            }
        }

        button("Copy") {
            enableWhen(isSelected)
            action {
                val extract = ContainerModifier.copy(selection!!)
                extractInfo.extract = extract
            }
        }
    }
}
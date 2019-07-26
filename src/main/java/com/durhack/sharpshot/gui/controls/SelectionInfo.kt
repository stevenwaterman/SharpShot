package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.core.control.ContainerModifier
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.util.globalExtract
import com.durhack.sharpshot.util.globalSelection
import com.durhack.sharpshot.util.globalSelectionProp
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class SelectionInfo : View() {
    private val containerView: ContainerView by inject()

    private val isSelected = globalSelectionProp.booleanBinding(globalSelectionProp) { it != null }
    private val selectionWidth = globalSelectionProp.integerBinding(globalSelectionProp) {
        if (it == null) {
            0
        }
        else {
            val range = it.xRange
            range.endInclusive - range.start
        }
    }
    private val selectionHeight = globalSelectionProp.integerBinding(globalSelectionProp) {
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
                ContainerModifier.clear(globalSelection!!)
                containerView.render()
            }
        }

        button("Cut") {
            enableWhen(isSelected)
            action {
                val extract = ContainerModifier.cut(globalSelection!!)
                globalExtract = extract
                containerView.render()
            }
        }

        button("Copy") {
            enableWhen(isSelected)
            action {
                val extract = ContainerModifier.copy(globalSelection!!)
                globalExtract = extract
            }
        }
    }
}
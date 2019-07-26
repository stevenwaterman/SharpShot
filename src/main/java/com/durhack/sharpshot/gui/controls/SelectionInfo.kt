package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.core.control.GlobalContainerModifier
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.util.range
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
        it?.xRange?.range ?: 0
    }
    private val selectionHeight = globalSelectionProp.integerBinding(globalSelectionProp) {
        it?.yRange?.range ?: 0
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
                GlobalContainerModifier.clear(globalSelection!!)
                globalSelection = null
                containerView.render()
            }
        }

        button("Cut") {
            enableWhen(isSelected)
            action {
                val extract = GlobalContainerModifier.cut(globalSelection!!)
                globalExtract = extract
                globalSelection = null
                containerView.render()
            }
        }

        button("Copy") {
            enableWhen(isSelected)
            action {
                val extract = GlobalContainerModifier.copy(globalSelection!!)
                globalSelection = null
                globalExtract = extract
            }
        }
    }
}
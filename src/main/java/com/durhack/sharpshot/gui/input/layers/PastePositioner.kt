package com.durhack.sharpshot.gui.input.layers

import com.durhack.sharpshot.core.control.GlobalContainerModifier
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.input.layers.popovers.PasteCoord
import com.durhack.sharpshot.gui.input.layers.popovers.PasteHover
import com.durhack.sharpshot.gui.util.addClickHandler
import com.durhack.sharpshot.gui.util.point
import com.durhack.sharpshot.util.globalExtract
import javafx.scene.input.MouseButton
import tornadofx.*

class PastePositioner : View() {
    private val containerView: ContainerView by inject()

    override val root = stackpane {
        id = "Paste Positioner"

        setOnMouseMoved {
            val captValidPasteArea = PasteHover.validPasteArea ?: return@setOnMouseMoved
            if (PasteHover.pasting) {
                val point = it.point
                val newCoord = containerView.getCoord(point).floor
                val clampedCoord = captValidPasteArea.clamp(newCoord)

                if (clampedCoord != PasteHover.lastCoord?.coord) {
                    PasteHover.lastCoord = PasteCoord(clampedCoord, pastable(newCoord))
                }
            }
        }

        addClickHandler {
            if (PasteHover.pasting) {
                if (it.button == MouseButton.PRIMARY) {
                    val captLastCoord = PasteHover.lastCoord ?: return@addClickHandler
                    if (captLastCoord.valid) {
                        GlobalContainerModifier.paste(globalExtract!!, captLastCoord.coord)

                        if (!it.isShiftDown) {
                            PasteHover.reset()
                        }


                        containerView.render()
                    }
                    it.consume()
                }
                else if (it.button == MouseButton.SECONDARY) {
                    PasteHover.reset()
                    it.consume()
                }
            }
        }
    }

    private fun pastable(location: Coordinate) = GlobalContainerModifier.canPlace(globalExtract!!, location)
}
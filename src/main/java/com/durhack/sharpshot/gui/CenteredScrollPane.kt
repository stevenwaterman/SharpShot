package com.durhack.sharpshot.gui

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Region
import tornadofx.*

class CenteredScrollPane : Fragment() {
    private lateinit var internalBox: HBox
    private val contentsWidth = SimpleDoubleProperty()
    private val contentsHeight = SimpleDoubleProperty()

    override val root = scrollpane {
        isFitToHeight = true
        isFitToWidth = true
        vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        hbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED

        val scroll = this
        vbox(alignment = Pos.CENTER) {
            minWidthProperty().bind(
                    Bindings.max(
                            scroll.viewportBoundsProperty()
                                    .doubleBinding(scroll.viewportBoundsProperty()) {
                                        it ?: return@doubleBinding 0.0
                                        it.width - 10.0
                                    },
                            contentsWidth))

            internalBox = hbox(alignment = Pos.CENTER) {
                minHeightProperty().bind(
                        Bindings.max(
                                scroll.viewportBoundsProperty()
                                        .doubleBinding(scroll.viewportBoundsProperty()) {
                                            it ?: return@doubleBinding 0.0
                                            it.height - 10.0
                                        },
                                contentsHeight))

            }
        }
    }

    fun setContent(contents: Region?) {
        internalBox.children.clear()
        if (contents != null) {
            internalBox.children.add(contents)
            contentsWidth.bind(contents.minWidthProperty())
            contentsHeight.bind(contents.minHeightProperty())
        }
    }
}
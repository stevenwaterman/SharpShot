package com.durhack.sharpshot.gui.container.menus.nodecreator.nodeforms

import com.durhack.sharpshot.core.nodes.input.InNode
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.util.BigIntSpinner
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

class InNodeForm(close: () -> Unit, success: (InNode) -> Unit) : AbstractNodeForm<InNode>(close, success) {
    private val spinner = BigIntSpinner()

    override val root = vbox {
        alignment = Pos.CENTER
        paddingAll = 16.0
        spacing = 4.0
        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(2.0)))
        background = Background(BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY))

        setOnMouseExited {
            if (it.target == this) {
                close()
            }
        }

        setOnMousePressed {
            if (it.button == MouseButton.SECONDARY) {
                close()
            }
        }

        setOnScroll {
            close()
        }

        spinner.root.editor.setOnKeyPressed {
            if(it.code == KeyCode.ENTER){
                success(InNode(spinner.value?.toInt(), Direction.UP))
                close()
            }
        }

        label("Which of the inputs should this node output?"){
            font = Font(18.0)
        }
        label("The first index is number 1. Press empty to fire blank bullets.")
        hbox(4.0) {
            alignment = Pos.CENTER

            add(spinner)

            button("Accept") {
                action {
                    success(InNode(spinner.value?.toInt(), Direction.UP))
                    close()
                }
            }
            button("Empty") {
                action {
                    success(InNode(null, Direction.UP))
                    close()
                }
            }
        }
    }
}
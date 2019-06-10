package com.durhack.sharpshot.gui.container.menus.nodecreator.nodeforms

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.gui.util.BigIntSpinner
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseButton
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*
import java.math.BigInteger

abstract class IntNodeForm<T: AbstractNode>(done: () -> Unit, success: (T) -> Unit, firstLineText: String, secondLineText: String, private val createNode: (BigInteger?) -> T): AbstractNodeForm<T>(done, success){
    private val spinner = BigIntSpinner()

    override val root = vbox {
        alignment = Pos.CENTER
        paddingAll = 16.0
        spacing = 4.0
        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(2.0)))
        background = Background(BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY))

        setOnMouseExited {
            if (it.target == this) {
                done()
            }
        }

        setOnMousePressed {
            if (it.button == MouseButton.SECONDARY) {
                done()
            }
        }

        addEventFilter(KeyEvent.KEY_PRESSED) {event ->
            when(event.code){
                KeyCode.ENTER -> accept()
            }
        }

        label(firstLineText){
            font = Font(18.0)
        }
        label(secondLineText)
        hbox(4.0) {
            alignment = Pos.CENTER

            add(spinner)

            button("Accept") {
                action {
                    accept()
                }
            }
            button("Empty") {
                action {
                    spinner.valueProp.set(null)
                    accept()
                }
            }
        }
    }

    private fun accept(){
        val value = spinner.valueProp.get()
        val node = createNode(value)
        success(node)
        done()
    }
}
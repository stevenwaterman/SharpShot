package com.durhack.sharpshot.gui.container.menus.createnode.nodeforms

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.gui.util.BigIntSpinner
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.text.Font
import tornadofx.*
import java.math.BigInteger

abstract class IntNodeForm<T: AbstractNode>(done: () -> Unit, success: (T) -> Unit, firstLineText: String, secondLineText: String, private val createNode: (BigInteger?) -> T): AbstractNodeForm<T>(done, success){
    private val spinner = BigIntSpinner()

    override fun focus() {
        spinner.root.requestFocus()
    }

    override val root = vbox {
        alignment = Pos.CENTER
        paddingAll = 16.0
        spacing = 4.0

        addEventFilter(KeyEvent.KEY_PRESSED) {event ->
            if (event.code in listOf(KeyCode.ENTER, KeyCode.TAB)){
                accept()
                event.consume()
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
                    spinner.value = null
                    accept()
                }
            }
        }
    }

    private fun accept(){
        val value = spinner.value
        val node = createNode(value)
        success(node)
        done()
    }
}
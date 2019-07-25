package com.durhack.sharpshot.gui.container.input.layers.popovers.createnode

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.gui.container.input.layers.popovers.createnode.nodebuttons.*
import javafx.scene.input.KeyEvent
import tornadofx.*

class SelectNodeType(onHover: (AbstractNodeButton) -> Unit,
                     showForm: (AbstractNodeForm) -> Unit,
                     nodeCreated: (AbstractNode) -> Unit) : Fragment() {
    private val perRow = 4
    private val gap = 2.0
    private val outerPadding = 4.0

    private val keys = listOf(
            "1",
            "2",
            "3",
            "4",
            "q",
            "w",
            "e",
            "r",
            "a",
            "s",
            "d",
            "f",
            "z",
            "x",
            "c",
            "v"
                             )

    private val buttons = listOf(
            InputNodeButton(onHover, showForm, nodeCreated),
            ListNodeButton(onHover, showForm, nodeCreated),
            ConstantNodeButton(onHover, showForm, nodeCreated),
            HaltNodeButton(onHover, showForm, nodeCreated),

            BranchNodeButton(onHover, showForm, nodeCreated),
            SplitterNodeButton(onHover, showForm, nodeCreated),
            VoidNodeButton(onHover, showForm, nodeCreated),
            RotateNodeButton(onHover, showForm, nodeCreated),

            ConditionalNodeButton(onHover, showForm, nodeCreated),
            MathNodeButton(onHover, showForm, nodeCreated),
            MemoryNodeButton(onHover, showForm, nodeCreated),
            SingleUseBranchButton(onHover, showForm, nodeCreated)
                                )

    override val root = gridpane {
        hgap = gap
        vgap = gap
        paddingAll = outerPadding

        buttons.zip(keys).forEachIndexed { index, (button, key) ->
            val column = index % perRow
            val row = index / perRow

            add(button.root, column, row)
            addEventHandler(KeyEvent.KEY_TYPED) {
                if (it.character == key) {
                    it.consume()
                    button.clicked()
                }
            }
        }
    }
}
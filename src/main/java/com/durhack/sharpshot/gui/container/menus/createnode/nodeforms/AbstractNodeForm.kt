package com.durhack.sharpshot.gui.container.menus.createnode.nodeforms

import com.durhack.sharpshot.core.nodes.AbstractNode
import tornadofx.*

abstract class AbstractNodeForm<T: AbstractNode>(protected val done: () -> Unit, protected val success: (T) -> Unit): Fragment(){
    abstract fun focus()
}
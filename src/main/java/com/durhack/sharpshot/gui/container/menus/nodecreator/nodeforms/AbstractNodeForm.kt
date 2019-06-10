package com.durhack.sharpshot.gui.container.menus.nodecreator.nodeforms

import com.durhack.sharpshot.core.nodes.AbstractNode
import tornadofx.*

abstract class AbstractNodeForm<T: AbstractNode>(private val close: () -> Unit, private val success: (T) -> Unit): Fragment()
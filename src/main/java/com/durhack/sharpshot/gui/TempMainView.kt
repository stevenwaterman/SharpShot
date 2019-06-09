package com.durhack.sharpshot.gui

import com.durhack.sharpshot.gui.container.menus.nodecreator.NodeCreator
import tornadofx.*

class TempMainView(): View(){
    private val creator = NodeCreator() {
        println(it)
        hideNodeCreator()
    }

    override val root = stackpane {
        button {
            action { showNodeCreator() }
            text = "Press"
        }
    }

    private fun showNodeCreator(){
        root.add(creator)
    }

    private fun hideNodeCreator(){
        root.children.remove(creator.root)
    }
}
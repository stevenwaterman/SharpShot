package com.durhack.sharpshot.gui

import com.durhack.sharpshot.gui.container.menus.createnode.CreateNodeMenu
import tornadofx.*

class TempMainView(): View(){
    private val creator = CreateNodeMenu() {
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
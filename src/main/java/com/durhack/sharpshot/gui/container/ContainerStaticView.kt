package com.durhack.sharpshot.gui.container

import tornadofx.*

class ContainerStaticView : View() {
    private val renderer = ContainerStaticRenderer()

    override val root = pane {
        id = "Container Static View"

        add(renderer)
    }

    fun render() {
        renderer.renderContainer(ContainerView.scale)
    }
}


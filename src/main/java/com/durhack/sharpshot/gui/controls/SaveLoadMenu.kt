package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.gui.container.ContainerController
import com.durhack.sharpshot.serialisation.ContainerSaveLoad
import com.durhack.sharpshot.util.container
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.text.Font
import tornadofx.*

class SaveLoadMenu: View(){
    private val controller: ContainerController by inject()

    override val root = vbox(4.0, Pos.CENTER) {
        label("Menu") {
            font = Font(18.0)
        }
        hbox(4.0, Pos.CENTER) {
            enableWhen(container.runningProp.not())
            button("Load"){
                action {
                    val success = ContainerSaveLoad.load()
                    if(success) controller.view.render()
                }
            }
            button("Save") {
                action {
                    ContainerSaveLoad.save()
                }
            }
        }
        button("Clear") {
            enableWhen(container.runningProp.not())
            action {
                controller.clear()
            }
        }

    }
}
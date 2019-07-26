package com.durhack.sharpshot.gui.controls

import com.durhack.sharpshot.gui.container.ContainerController
import com.durhack.sharpshot.gui.util.not
import com.durhack.sharpshot.serialisation.ContainerSaveLoad
import com.durhack.sharpshot.serialisation.SaveLoadType
import com.durhack.sharpshot.util.globalContainer
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class SaveLoadMenu : View() {
    private val controller: ContainerController by inject()

    override val root = vbox(4.0, Pos.CENTER) {
        id = "Save Load Menu"

        label("Menu") {
            font = Font(18.0)
        }
        hbox(4.0, Pos.CENTER) {
            enableWhen(globalContainer.runningProp.not())
            button("Load") {
                isFocusTraversable = false
                action {
                    val success = ContainerSaveLoad.load(SaveLoadType.CONTAINER)
                    if (success) controller.view.render()
                }
            }
            button("Save") {
                isFocusTraversable = false
                action {
                    ContainerSaveLoad.save(SaveLoadType.CONTAINER)
                }
            }
        }
        button("Clear") {
            isFocusTraversable = false
            enableWhen(globalContainer.runningProp.not())
            action {
                controller.clear()
            }
        }

    }
}
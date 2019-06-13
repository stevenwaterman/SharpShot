package com.durhack.sharpshot.serialisation

import com.durhack.sharpshot.core.state.Container
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.util.container
import javafx.scene.Scene
import javafx.scene.SceneAntialiasing
import javafx.scene.SnapshotParameters
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import tornadofx.FileChooserMode
import tornadofx.chooseFile
import tornadofx.runAsync
import java.io.File

object ContainerSaveLoad {
    private val snapshotParams = SnapshotParameters().run {
        fill = Color.valueOf("F4F4F4")
        isDepthBuffer = true
        return@run this
    }

    /**
     * If path is null, a file chooser will pop up
     * If title is null, a text initialise will pop up
     * If title is blank (empty space only), no title will be added
     */
    fun save(containerView: ContainerView): Boolean {
        val file =
                chooseFile("Save Location",
                           listOf(FileChooser.ExtensionFilter("Png Images", "*.png")).toTypedArray(),
                           FileChooserMode.Save
                          ) {
                    initialDirectory = File(System.getProperty("user.dir"))
                    initialFileName = "export"
                }.firstOrNull() ?: return false
        return saveToFile(containerView, file)
    }

    private fun saveToFile(containerView: ContainerView, file: File): Boolean {
        val pane = containerView.root

        val width = pane.minWidth
        val height = pane.minHeight

        //You need to put it in a scene to get it to layout properly
        Scene(pane, width, height, true, SceneAntialiasing.BALANCED)
        val tempImage = pane.snapshot(snapshotParams, null)
        //val image = SwingFXUtils.fromFXImage(tempImage, null)

        val json = Serialiser.saveContainer(container)
        runAsync {
            //Png.write(file.absolutePath, image, json)
        }
        return true
    }

    fun load(): Container? {
        val file = chooseFile("Select Image",
                              listOf(FileChooser.ExtensionFilter("Png Images", "*.png")).toTypedArray(),
                              FileChooserMode.Single
                             ) {
            initialDirectory = File(System.getProperty("user.dir"))
        }.firstOrNull() ?: return null
        return loadFromFile(file)
    }

    private fun loadFromFile(file: File): Container {
        val json = Png.read(file.absolutePath)
        return Serialiser.loadContainer(json)
    }
}
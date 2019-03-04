package com.durhack.sharpshot.serialisation

import com.durhack.sharpshot.logic.Container
import javafx.embed.swing.SwingFXUtils
import javafx.scene.Scene
import javafx.scene.SceneAntialiasing
import javafx.scene.SnapshotParameters
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

object ContainerSaveLoad {
    private val snapshotParams = SnapshotParameters().run {
        fill = Color.valueOf("F4F4F4")
        isDepthBuffer = true
        return@run this
    }

    /**
     * If path is null, a file chooser will pop up
     * If title is null, a text input will pop up
     * If title is blank (empty space only), no title will be added
     */
    fun save(container: Container): Boolean {
        val file =
                chooseFile("Save",
                           listOf(FileChooser.ExtensionFilter("Sharpshot File", "*.ss")).toTypedArray(),
                           FileChooserMode.Save
                          ) {
                    initialDirectory = File(System.getProperty("user.dir"))
                    initialFileName = "program.ss"
                }.firstOrNull() ?: return false
        return save(container, file)
    }

    fun save(container: Container,
             file: File): Boolean {
        val containerView = StaticContainerView(container)
        val pane = containerView.root

        val width = pane.minWidth
        val height = pane.minHeight

        //You need to put it in a scene to get it to layout properly
        Scene(pane, width, height, true, SceneAntialiasing.BALANCED)
        val tempImage = pane.snapshot(snapshotParams, null)
        val image = SwingFXUtils.fromFXImage(tempImage, null)

        val json = Serialiser.saveContainer(container)
        runAsync {
            Png.write(file.absolutePath, image, json)
        }
        return true
    }

    fun load(): Container? {
        val file = chooseFile("Load",
                              listOf(FileChooser.ExtensionFilter("Sharpshot File", "*.ss")).toTypedArray(),
                              FileChooserMode.Single
                             ) {
            initialDirectory = File(System.getProperty("user.dir"))
        }.firstOrNull() ?: return null
        return load(file)
    }

    fun load(file: File): Container {
        val json = Png.read(file.absolutePath)
        return Serialiser.loadContainer(json)
    }
}
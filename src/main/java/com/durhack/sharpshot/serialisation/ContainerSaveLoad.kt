package com.durhack.sharpshot.serialisation

import com.durhack.sharpshot.core.state.Container
import com.durhack.sharpshot.gui.container.ContainerStaticRenderer
import com.durhack.sharpshot.gui.container.ContainerStaticView
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.util.container
import javafx.embed.swing.SwingFXUtils
import javafx.scene.Scene
import javafx.scene.SceneAntialiasing
import javafx.scene.SnapshotParameters
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import tornadofx.FileChooserMode
import tornadofx.add
import tornadofx.chooseFile
import tornadofx.runAsync
import java.io.File
import javax.imageio.ImageIO

object ContainerSaveLoad {
    private val snapshotParams = SnapshotParameters().run {
        fill = Color.valueOf("F4F4F4")
        isDepthBuffer = true
        return@run this
    }

    /**
     * If path is null, a file chooser will pop up
     * If title is null, a text start will pop up
     * If title is blank (empty space only), no title will be added
     */
    fun save(): Boolean {
        val file =
                chooseFile("Save Location",
                        listOf(FileChooser.ExtensionFilter("Png Images", "*.png")).toTypedArray(),
                        FileChooserMode.Save
                ) {
                    initialDirectory = File(System.getProperty("user.dir"))
                    initialFileName = "export"
                }.firstOrNull() ?: return false
        return saveToFile(file)
    }

    private val renderer = ContainerStaticRenderer()
    private val pane = Pane().apply { add(renderer) }
    private val scene = Scene(pane, pane.width, pane.height, true, SceneAntialiasing.BALANCED)
    private fun saveToFile(file: File): Boolean {
        renderer.render(24)
        val tempImage = pane.snapshot(snapshotParams, null)
        val image = SwingFXUtils.fromFXImage(tempImage, null)

        val json = Serialiser.saveContainer(container)
        runAsync {
            Png.write(file.absolutePath, image, json)
        }
        return true
    }

    fun load(): Boolean {
        val file = chooseFile("Select Image",
                listOf(FileChooser.ExtensionFilter("Png Images", "*.png")).toTypedArray(),
                FileChooserMode.Single)
        {
            initialDirectory = File(System.getProperty("user.dir"))
        }.firstOrNull() ?: return false
        loadFromFile(file)
        return true
    }

    private fun loadFromFile(file: File) {
        val json = Png.read(file.absolutePath)
        val newContainer = Serialiser.loadContainer(json)
        container.setTo(newContainer)
    }
}
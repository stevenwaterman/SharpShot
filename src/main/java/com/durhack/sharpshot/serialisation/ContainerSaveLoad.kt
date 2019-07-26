package com.durhack.sharpshot.serialisation

import com.durhack.sharpshot.gui.container.ContainerStaticRenderer
import com.durhack.sharpshot.gui.container.Extract
import com.durhack.sharpshot.gui.util.CoordinateRange2D
import com.durhack.sharpshot.util.globalContainer
import com.durhack.sharpshot.util.globalExtract
import javafx.embed.swing.SwingFXUtils
import javafx.scene.Scene
import javafx.scene.SceneAntialiasing
import javafx.scene.SnapshotParameters
import javafx.scene.layout.Pane
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
     * If title is null, a text start will pop up
     * If title is blank (empty space only), no title will be added
     */
    fun save(source: SaveLoadType): Boolean {
        val file =
                chooseFile("Save Location",
                           listOf(FileChooser.ExtensionFilter("Png Images", "*.png")).toTypedArray(),
                           FileChooserMode.Save
                          ) {
                    initialDirectory = File(System.getProperty("user.dir"))
                    initialFileName = "export"
                }.firstOrNull() ?: return false
        return saveToFile(source, file)
    }

    private val renderer = ContainerStaticRenderer()
    private val pane = Pane().apply { add(renderer) }

    init {
        Scene(pane, pane.width, pane.height, true, SceneAntialiasing.BALANCED)
    }

    private fun saveToFile(source: SaveLoadType, file: File): Boolean {
        val extract = when (source) {
            SaveLoadType.CONTAINER -> {
                renderer.renderContainer(24)
                Extract(globalContainer.nodes, CoordinateRange2D(globalContainer.width, globalContainer.height))
            }
            SaveLoadType.EXTRACT -> {
                renderer.renderExtract(24)
                globalExtract
            }
        } ?: return false

        val tempImage = pane.snapshot(snapshotParams, null)
        val image = SwingFXUtils.fromFXImage(tempImage, null)

        val json = Serialiser.save(extract)
        runAsync {
            Png.write(file.absolutePath, image, json)
        }
        return true
    }

    fun load(target: SaveLoadType): Boolean {
        val file = chooseFile("Select Image",
                              listOf(FileChooser.ExtensionFilter("Png Images", "*.png")).toTypedArray(),
                              FileChooserMode.Single)
        {
            initialDirectory = File(System.getProperty("user.dir"))
        }.firstOrNull() ?: return false
        loadFromFile(target, file)
        return true
    }

    private fun loadFromFile(target: SaveLoadType, file: File) {
        val json = Png.read(file.absolutePath)

        val extract = Serialiser.load(json)

        when (target) {
            SaveLoadType.CONTAINER -> globalContainer.setTo(extract)
            SaveLoadType.EXTRACT -> globalExtract = extract
        }
    }
}
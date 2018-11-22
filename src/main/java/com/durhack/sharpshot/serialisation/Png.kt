package com.durhack.sharpshot.serialisation

import com.sun.imageio.plugins.png.PNGMetadata
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageTypeSpecifier
import javax.imageio.metadata.IIOMetadataNode

class Png {
    companion object {
        fun write(path: String, image: BufferedImage, txtValue: String) {
            val writer = ImageIO.getImageWritersByFormatName("png").next()

            val writeParam = writer.defaultWriteParam
            val typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB)

            //adding metadata
            val metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam)

            val textEntry = IIOMetadataNode("tEXtEntry")
            textEntry.setAttribute("keyword", "comment")
            textEntry.setAttribute("value", txtValue)

            val text = IIOMetadataNode("tEXt")
            text.appendChild(textEntry)

            val root = IIOMetadataNode("javax_imageio_png_1.0")
            root.appendChild(text)

            metadata.mergeTree("javax_imageio_png_1.0", root)

            //writing the data
            val file = File(path)
            ImageIO.createImageOutputStream(file).use { os ->
                writer.output = os
                val iioImage = IIOImage(image, null, metadata)
                writer.write(metadata, iioImage, writeParam)
            }
        }

        fun read(path: String): String {
            val imageReader = ImageIO.getImageReadersByFormatName("png").next()

            val file = File(path)
            file.inputStream().use { fis ->
                imageReader.setInput(ImageIO.createImageInputStream(fis), true)

                // read metadata of first image
                val metadata = imageReader.getImageMetadata(0)

                //this cast helps getting the contents
                val pngmeta = metadata as PNGMetadata
                val childNodes = pngmeta.standardTextNode.childNodes

                return (0..childNodes.length)
                        .map { index -> childNodes.item(index) }
                        .first { node ->
                            node.attributes.getNamedItem("keyword").nodeValue == "comment"
                        }
                        .attributes
                        .getNamedItem("value")
                        .nodeValue
            }
        }
    }
}
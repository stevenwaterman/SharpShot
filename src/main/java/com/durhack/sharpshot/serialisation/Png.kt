package com.durhack.sharpshot.serialisation

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageTypeSpecifier
import javax.imageio.metadata.IIOMetadataFormatImpl
import javax.imageio.metadata.IIOMetadataNode

class Png {
    companion object {
        fun write(path: String, image: BufferedImage, metadataString: String) {
            val textEntry = IIOMetadataNode("TextEntry")
            textEntry.setAttribute("keyword", "comment")
            textEntry.setAttribute("value", metadataString)

            val text = IIOMetadataNode("Text")
            text.appendChild(textEntry)

            val root = IIOMetadataNode(IIOMetadataFormatImpl.standardMetadataFormatName)
            root.appendChild(text)

            val writer = ImageIO.getImageWritersByFormatName("png").next()
            val writeParam = writer.defaultWriteParam
            val typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB)
            val metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam)
            metadata.mergeTree(IIOMetadataFormatImpl.standardMetadataFormatName, root)

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
                val root = metadata.getAsTree(IIOMetadataFormatImpl.standardMetadataFormatName) as IIOMetadataNode

                val childNodes = root.getElementsByTagName("TextEntry")

                return (0..childNodes.length)
                        .map(childNodes::item)
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
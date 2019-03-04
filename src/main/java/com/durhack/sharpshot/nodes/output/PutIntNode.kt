package com.durhack.sharpshot.nodes.output

import com.durhack.sharpshot.gui.shapes.Diamond
import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.logic.Direction
import com.durhack.sharpshot.nodes.INode
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import javafx.scene.paint.Color
import java.math.BigInteger

class PutIntNode : AbstractOutputNode() {

    override fun run(bullet: Bullet) : Map<Direction, BigInteger?> = emptyMap<Direction, BigInteger>()

    override fun print(bullet: Bullet) : String = bullet.value.toString()

    override fun graphic() = Diamond(rotation,
            Color.web("#FFFF00"),
            "INT")

    override fun reset() {}

    override val type = "put int"

    override val tooltip = "Prints an integer that of a bullet that hits it"
    override val factory = { PutIntNode() }

    override fun toJson(): JsonElement { return super.toJson().asJsonObject }

    override val jsonFactory: (JsonObject) -> INode = { json ->
        val node = PutIntNode()
        val rotation = json["rotation"].asInt
        node.rotation = Direction.ofQuarters(rotation)
        node
    }
}

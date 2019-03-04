package com.durhack.sharpshot.nodes.output

import com.durhack.sharpshot.logic.Bullet
import com.durhack.sharpshot.nodes.INode

abstract class AbstractOutputNode : INode() {
    abstract fun print(bullet: Bullet): String
}

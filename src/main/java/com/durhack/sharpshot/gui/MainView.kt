package com.durhack.sharpshot.gui

import com.durhack.sharpshot.core.nodes.routing.BranchNode
import com.durhack.sharpshot.core.state.Bullet
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.core.state.Direction
import com.durhack.sharpshot.gui.container.CenteredScrollPane
import com.durhack.sharpshot.gui.container.ContainerController
import com.durhack.sharpshot.util.container
import javafx.scene.input.KeyEvent
import tornadofx.*
import java.math.BigInteger

class MainView(): View(){
    private val controller: ContainerController by inject()
    private val scrollPane: CenteredScrollPane by inject()

    override val root = borderpane {
        center{
            add(scrollPane)

            //TODO remove
            container.nodes[Coordinate(1, 1)] = BranchNode(Direction.UP)
            container.nodes[Coordinate(2, 1)] = BranchNode(Direction.RIGHT)
            container.nodes[Coordinate(3, 1)] = BranchNode(Direction.DOWN)
            container.nodes[Coordinate(4, 1)] = BranchNode(Direction.LEFT)
            container.nodes[Coordinate(10, 5)] = BranchNode(Direction.UP)
            val bullet = Bullet(Coordinate(2,2), Direction.DOWN, BigInteger.TEN)
            container.bullets.add(bullet)
            controller.view.render()

            addEventFilter(KeyEvent.KEY_PRESSED){
                println("${it.target} ${it.code}")
            }
        }
    }
}
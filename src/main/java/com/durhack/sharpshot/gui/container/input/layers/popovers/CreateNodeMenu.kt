package com.durhack.sharpshot.gui.container.input.layers.popovers

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.core.state.Coordinate
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.input.layers.popovers.createnode.NodeInfo
import com.durhack.sharpshot.gui.container.input.layers.popovers.createnode.SelectNodeType
import com.durhack.sharpshot.gui.container.input.layers.popovers.createnode.nodebuttons.AbstractNodeForm
import com.durhack.sharpshot.gui.controls.ScrollZoomPane
import com.durhack.sharpshot.gui.util.addClickHandler
import com.durhack.sharpshot.util.container
import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.scene.Node
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*

class CreateNodeMenu : View() {
    private val padding = 12.0
    private val borderWidth = 2.0
    private val allBorder = Border(BorderStroke(Color.BLACK,
                                                BorderStrokeStyle.SOLID,
                                                CornerRadii.EMPTY,
                                                BorderWidths(borderWidth)))
    private val allBackground = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
    private val containerView: ContainerView by inject()
    private lateinit var coordinate: Coordinate
    private val info = NodeInfo()
    private val selector = SelectNodeType(
            { info.show(it) },
            { showForm(it) },
            {
                addNode(coordinate, it)
                hideAll()
            }
                                         )
    private val chooser = hbox(8.0) {
        id = "Node Chooser"
        add(selector)
        add(info)
    }

    private val formPane = pane {
        id = "Node Form Pane"
    }

    override val root = stackpane {
        id = "Create Node Menu"

        paddingAll = this@CreateNodeMenu.padding
        border = allBorder
        background = allBackground

        add(chooser)
        add(formPane)
        hide()

        addEventHandler(MouseEvent.MOUSE_EXITED) {
            hideAll()
        }

        addClickHandler {
            if (it.button == MouseButton.SECONDARY) {
                hideAll()
            }
            it.consume()
        }

        addEventFilter(KeyEvent.KEY_PRESSED) {
            if (it.code == KeyCode.ESCAPE) {
                hideAll()
                it.consume()
            }
        }
    }

    init {
        ContainerView.innerScaleProp.addListener { _ -> hideAll() }
    }

    fun show(coordinate: Coordinate, click: Point2D) {
        this.coordinate = coordinate

        val idealOffset = idealOffset()
        val idealLocation = click + idealOffset

        val clampedOffset = clampLocation(idealLocation)
        val clampedLocation = idealLocation + clampedOffset

        root.layoutX = clampedLocation.x
        root.layoutY = clampedLocation.y
        showChooser()
    }

    private fun idealOffset(): Point2D {
        val xOffset = padding + (selector.root.width / 2) + borderWidth
        val yOffset = padding + (selector.root.height / 2) + borderWidth
        return Point2D(-xOffset, -yOffset)
    }

    private val scrollZoomPane: ScrollZoomPane by inject()
    private fun clampLocation(location: Point2D): Point2D {
        var parentLocation = location
        var parent: Node = root.parent
        //TODO is there not a better way to do this? - or at least make it into a helper function
        while (parent != scrollZoomPane.root) {
            parentLocation = parent.localToParent(parentLocation)
            parent = parent.parent
        }

        val scrollPaneLocation = parentLocation

        val minX = scrollPaneLocation.x
        val maxX = minX + root.width
        val maxAllowableX = scrollZoomPane.root.width

        val xOffset = when {
            minX < 0 -> -minX
            maxX > maxAllowableX -> maxAllowableX - maxX
            else -> 0.0
        }

        val minY = scrollPaneLocation.y
        val maxY = minY + root.height
        val maxAllowableY = scrollZoomPane.root.height

        val yOffset = when {
            minY < 0 -> -minY
            maxY > maxAllowableY -> maxAllowableY - maxY
            else -> 0.0
        }

        return Point2D(xOffset, yOffset)
    }

    private fun showChooser() {
        chooser.show()

        formPane.hide()
        root.show()
        selector.root.requestFocus()
    }

    private var form: AbstractNodeForm? = null

    private fun showForm(form: AbstractNodeForm) {
        formPane.children.clear()
        formPane.children.add(form.root)
        formPane.show()

        this.form = form

        chooser.hide()
        root.show()
        form.focus()
    }

    private fun hideAll() {
        root.hide()
        info.reset()
        //TODO i probably just broke keyboard shortcuts
        //inputLayer.root.requestFocus()
        form?.onHide()
    }

    private fun addNode(coordinate: Coordinate, node: AbstractNode?) {
        node ?: return
        container.nodes[coordinate] = node
        containerView.render()
    }
}
package com.durhack.sharpshot.gui.container.menus.createnode

import com.durhack.sharpshot.core.nodes.AbstractNode
import com.durhack.sharpshot.gui.container.ContainerView
import com.durhack.sharpshot.gui.container.menus.createnode.nodeforms.AbstractNodeForm
import com.durhack.sharpshot.registry.RegistryEntry
import com.durhack.sharpshot.util.plus
import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*

class CreateNodeMenu(private val onNodeCreated: (AbstractNode?) -> Unit) : Fragment() {
    val padding = 12.0
    private val borderWidth = 2.0
    private val allBorder = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(borderWidth)))
    private val allBackground = Background(BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY))

    private val info = NodeInfo()
    private val selector = SelectNodeType({chosen(it)}, {info.show(it)})
    private val chooser = hbox(8.0) {
        add(selector)
        add(info)
    }

    private val formPane = pane()

    override val root = stackpane {
        paddingAll = this@CreateNodeMenu.padding
        border = allBorder
        background = allBackground

        add(chooser)
        add(formPane)
        hide()

        addEventHandler(MouseEvent.MOUSE_EXITED){
            hideAll()
        }

        addEventHandler(MouseEvent.MOUSE_PRESSED){
            if(it.button == MouseButton.SECONDARY){
                hideAll()
            }
            it.consume()
        }

        addEventFilter(KeyEvent.KEY_PRESSED){
            if(it.code == KeyCode.ESCAPE){
                hideAll()
                it.consume()
            }
        }
    }

    init {
        ContainerView.scaleProp.addListener { _ -> hideAll() }
    }

    private fun chosen(entry: RegistryEntry<out AbstractNode>?) {
        hideAll()
        entry ?: return

        val form = entry.getNodeForm(
                close = { formPane.hide() },
                success = { node ->
                    onNodeCreated(node)
                    hideAll()
                })

        if (form == null) {
            onNodeCreated(entry.createNode())
        }
        else {
            showForm(form)
        }
    }

    fun show(click: Point2D) {
        val idealOffset = idealOffset()
        val idealLocation = click + idealOffset

        val clampedOffset = clampLocation(idealLocation)
        val clampedLocation = idealLocation + clampedOffset

        root.layoutX = clampedLocation.x
        root.layoutY = clampedLocation.y
        showChooser()
    }

    private fun idealOffset(): Point2D{
        val xOffset = padding + (selector.root.width / 2) + borderWidth
        val yOffset = padding + (selector.root.height / 2) + borderWidth
        return Point2D(-xOffset, -yOffset)
    }

    private fun clampLocation(location: Point2D): Point2D {
        val sceneLocation = root.parent.localToScene(location)

        val minX = sceneLocation.x
        val maxX = minX + root.width
        val maxAllowableX = root.scene.width

        val xOffset = when {
            minX < 0 -> -minX
            maxX > maxAllowableX -> maxAllowableX - maxX
            else -> 0.0
        }

        val minY = sceneLocation.y
        val maxY = minY + root.height
        val maxAllowableY = root.scene.height

        val yOffset = when{
            minY < 0 -> -minY
            maxY > maxAllowableY -> maxAllowableY - maxY
            else -> 0.0
        }

        return Point2D(xOffset, yOffset)
    }

    private fun showChooser(){
        chooser.show()

        formPane.hide()
        root.show()
        root.requestFocus()
    }

    private fun showForm(form: AbstractNodeForm<*>){
        formPane.children.clear()
        formPane.children.add(form.root)
        formPane.show()

        chooser.hide()
        root.show()
        form.focus()
    }

    private fun hideAll(){
        root.hide()
        info.reset()
    }
}
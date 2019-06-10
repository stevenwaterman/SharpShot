package com.durhack.sharpshot.util

import com.sun.javafx.scene.control.skin.ScrollPaneSkin
import javafx.beans.value.ChangeListener
import javafx.geometry.Bounds
import javafx.scene.control.ScrollPane

/**
 * Basically, the scroll pane skin keeps adding a listener that we don't want that breaks out zooming.
 * Rather than copy the whole class, we use this black magic
 * Trust me, it's better than the alternative
 */
class HackScrollPaneSkin(scrollPane: ScrollPane): ScrollPaneSkin(scrollPane){

    private val bcl: ChangeListener<in Bounds>

    init {
        val boundsChangeListenerField = ScrollPaneSkin::class.java.getDeclaredField("boundsChangeListener")
        boundsChangeListenerField.isAccessible = true
        val bcl = boundsChangeListenerField.get(this)
        @Suppress("UNCHECKED_CAST") //KILL ME
        this.bcl = bcl as ChangeListener<in Bounds>
    }

    override fun handleControlPropertyChanged(p: String?) {
        super.handleControlPropertyChanged(p)
        skinnable.content.layoutBoundsProperty().removeListener(bcl as ChangeListener<in Bounds>)
    }
}
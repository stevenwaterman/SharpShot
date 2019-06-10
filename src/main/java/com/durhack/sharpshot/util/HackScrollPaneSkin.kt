package com.durhack.sharpshot.util

import com.sun.javafx.scene.control.skin.ScrollPaneSkin
import javafx.beans.value.ChangeListener
import javafx.geometry.Bounds
import javafx.scene.control.ScrollPane

/**
 * Basically, the scroll pane skin keeps adding a listener that we don't want because it breaks our zooming.
 * Rather than copy the whole class, we use this black magic
 * Trust me, it's better than the alternative
 */
class HackScrollPaneSkin(scrollPane: ScrollPane): ScrollPaneSkin(scrollPane){

    private val bcl: ChangeListener<in Bounds>

    init {
        val boundsChangeListenerField = ScrollPaneSkin::class.java.getDeclaredField("boundsChangeListener")
        boundsChangeListenerField.isAccessible = true
        @Suppress("UNCHECKED_CAST") //KILL ME
        bcl = boundsChangeListenerField.get(this) as ChangeListener<in Bounds>
    }

    /**
     * This method adds the listener but we can't just copy the code from the code from the superclass with the line
     * commented out because it calls a load of private methods in the superclass
     * Instead we just add it and then remove it again KILL ME
     */
    override fun handleControlPropertyChanged(p: String?) {
        super.handleControlPropertyChanged(p)
        skinnable.content.layoutBoundsProperty().removeListener(bcl)
    }
}
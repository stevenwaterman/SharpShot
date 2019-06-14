package com.durhack.sharpshot.util

import javafx.beans.value.ChangeListener
import javafx.geometry.Bounds
import javafx.scene.control.ScrollPane
import javafx.scene.control.skin.ScrollPaneSkin

/**
 * Basically, the scroll pane skin keeps adding a TransparencyListener that we don't want because it breaks our zooming.
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

}
package com.durhack.sharpshot.util

import javafx.beans.property.SimpleIntegerProperty

class MinMaxIntProperty(private val min: Int, initial: Int, private val max: Int) : SimpleIntegerProperty(initial) {
    override fun set(newValue: Int) {
        val clamped = newValue.clamp(min, max)
        super.set(clamped)
    }
}
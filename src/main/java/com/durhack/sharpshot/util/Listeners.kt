package com.durhack.sharpshot.util

class ValueListeners<T> : (T) -> Unit {
    override fun invoke(value: T) {
        listeners.forEach { it(value) }
    }

    private val listeners = mutableListOf<(T) -> Unit>()

    fun add(listener: (T) -> Unit) {
        listeners.add(listener)
    }

    fun remove(listener: (T) -> Unit) {
        listeners.remove(listener)
    }
}

class Listeners : () -> Unit {
    override fun invoke() {
        listeners.forEach { it() }
    }

    private val listeners = mutableListOf<() -> Unit>()

    fun add(listener: () -> Unit) {
        listeners.add(listener)
    }

    fun remove(listener: () -> Unit) {
        listeners.remove(listener)
    }
}
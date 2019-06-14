package com.durhack.sharpshot.util

import java.util.*
import kotlin.concurrent.timerTask

class KTimer(name: String) {
    val timer = Timer(name)

    fun schedule(delayMs: Long, func: () -> Unit) {
        val task = timerTask { func() }
        timer.schedule(task, delayMs)
    }
}
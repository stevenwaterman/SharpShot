package com.durhack.sharpshot.gui

import tornadofx.*

class Program : App(MainView::class)

fun main(args: Array<String>) {
    launch<Program>()
}
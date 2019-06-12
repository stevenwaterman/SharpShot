package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.util.KTimer
import com.durhack.sharpshot.util.container
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.math.BigInteger

class ContainerController : Controller() {
    val view: ContainerView by inject()

    private val timer: KTimer = KTimer("Post-Animation Render Timer")
    private val out: ObservableList<BigInteger?> = FXCollections.observableArrayList()
    val outputs = FXCollections.unmodifiableObservableList(out)
    var ticks = 0

    fun start(input: List<BigInteger?>) {
        container.initialise(input)
        view.render()
    }

    fun quickTick() {
        val report = container.tick()
        ticks++
        view.render()
        out.addAll(report.outputs)
    }

    fun animatedTick(lengthMs: Long) {
        val report = container.tick()
        ticks++
        view.animate(report.collisionReport, lengthMs)
        timer.schedule(lengthMs, view::render)
        out.addAll(report.outputs)
    }

    fun simulate(ticks: Int) {
        repeat(ticks){
            val report = container.tick()
            this.ticks++
            out.addAll(report.outputs)

            if(report.halted) return@repeat
        }

        view.render()
    }

    fun reset() {
        container.resetNodes()
        container.clearBullets()
        out.clear()
        ticks = 0
        view.render()
    }

    fun clear() {
        container.clearNodes()
        container.clearBullets()
        out.clear()
        ticks = 0
        view.render()
    }
}
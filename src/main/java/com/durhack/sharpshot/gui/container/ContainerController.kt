package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.core.state.Container
import com.durhack.sharpshot.util.KTimer
import java.math.BigInteger

class ContainerController(val containerView: ContainerView) {
    private val container: Container get() = containerView.container
    private val timer: KTimer = KTimer("Post-Animation Render Timer")
    private val out: MutableList<BigInteger?> = mutableListOf()
    val outputs: List<BigInteger?> get() = out.toList()
    var ticks = 0

    fun start(input: List<BigInteger?>) {
        container.launch(input)
    }

    fun quickTick() {
        val report = container.tick()
        ticks++
        containerView.render()
        out.addAll(report.outputs)
    }

    fun animatedTick(lengthMs: Long) {
        val report = container.tick()
        ticks++
        containerView.animate(report.collisionReport, lengthMs)
        timer.schedule(lengthMs, containerView::render)
        out.addAll(report.outputs)
    }

    fun simulate(ticks: Int) {
        repeat(ticks){
            val report = container.tick()
            this.ticks++
            out.addAll(report.outputs)

            if(report.halted) return@repeat
        }

        containerView.render()
    }

    fun reset() {
        container.resetNodes()
        container.clearBullets()
        out.clear()
        ticks = 0
        containerView.render()
    }

    fun clear() {
        container.clearNodes()
        container.clearBullets()
        out.clear()
        ticks = 0
        containerView.render()
    }
}
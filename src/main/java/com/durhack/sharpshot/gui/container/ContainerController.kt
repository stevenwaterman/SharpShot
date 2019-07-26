package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.gui.util.ObservableOutput
import com.durhack.sharpshot.gui.util.ui
import com.durhack.sharpshot.util.KTimer
import com.durhack.sharpshot.util.globalContainer
import javafx.beans.binding.BooleanExpression
import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*
import java.math.BigInteger

class ContainerController : Controller() {
    val view: ContainerView by inject()

    private val timer: KTimer = KTimer("Post-Animation Render Timer")

    private val innerOutputProp = ObservableOutput()
    val outputStringProp = innerOutputProp.stringObservable.ui()

    private val innerTickProp = SimpleIntegerProperty(0)
    val tickProp = innerTickProp.ui()
    var ticks by innerTickProp
        private set

    private val innerAnimatingProp = SimpleBooleanProperty(false)
    val animatingProp = innerAnimatingProp.ui()
    var animating by innerAnimatingProp
        private set

    private val innerPlayingProp = SimpleBooleanProperty(false)
    val playingProp = innerPlayingProp.ui()
    var playing by innerPlayingProp
        private set

    private val stoppingPropInternal = SimpleBooleanProperty(false)
    val stoppingProp: BooleanExpression = ReadOnlyBooleanWrapper.booleanExpression(stoppingPropInternal)
    var stopping by stoppingPropInternal
        private set

    private val simulatingPropInternal = SimpleBooleanProperty(false)
    val simulatingProp: BooleanExpression = ReadOnlyBooleanWrapper.booleanExpression(simulatingPropInternal)
    var simulating by simulatingPropInternal
        private set

    val idleProp = animatingProp.booleanBinding(playingProp,
                                                simulatingProp) { !animating && !playing && !simulating }.ui()
    val idle by idleProp

    fun start(input: List<BigInteger?>) {
        innerOutputProp.clear()
        ticks = 0
        globalContainer.start(input)
        view.render()
    }

    fun quickTick() {
        val report = globalContainer.tick()
        view.render()

        ticks++
        innerOutputProp.addAll(report.outputs)
    }

    fun animatedTick(lengthMs: Long, onFinish: () -> Unit = {}) {
        animating = true
        val report = globalContainer.tick()
        view.animate(report.collisionReport, lengthMs)
        timer.schedule(lengthMs) {
            view.render()
            onFinish()
            animating = false
        }

        innerOutputProp.addAll(report.outputs)
        ticks++
    }

    fun simulate(simulationTicks: Int) {
        simulating = true
        runAsync {
            val innerOut = mutableListOf<BigInteger?>()
            var innerTicks = 0
            for (i in (1..simulationTicks)) {
                val report = globalContainer.tick()
                innerTicks++
                innerOut.addAll(report.outputs)
                if (report.halted) break
            }

            view.render()
            ticks += innerTicks
            innerOutputProp.addAll(innerOut)
            simulating = false
        }
    }

    fun reset() {
        globalContainer.reset()
        view.render()
    }

    fun clear() {
        globalContainer.clear()
        innerOutputProp.clear()
        ticks = 0
        view.render()
    }

    private fun recursiveAnimate(lengthMs: Long) {
        animatedTick(lengthMs) {
            if (globalContainer.running && !stopping) {
                recursiveAnimate(lengthMs)
            }
            else {
                playing = false
                stopping = false
            }
        }
    }

    fun play(lengthMs: Long) {
        playing = true
        recursiveAnimate(lengthMs)
    }

    fun stopPlaying() {
        stopping = true
    }
}
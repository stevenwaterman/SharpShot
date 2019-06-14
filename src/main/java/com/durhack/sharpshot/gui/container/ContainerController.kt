package com.durhack.sharpshot.gui.container

import com.durhack.sharpshot.gui.util.ui
import com.durhack.sharpshot.util.KTimer
import com.durhack.sharpshot.util.container
import javafx.beans.binding.BooleanExpression
import javafx.beans.binding.IntegerExpression
import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.math.BigInteger
import java.util.*

class ContainerController : Controller() {
    val view: ContainerView by inject()

    private val playTimer: KTimer = KTimer("Play timer")
    private val timer: KTimer = KTimer("Post-Animation Render Timer")
    private val out: ObservableList<BigInteger?> = FXCollections.observableArrayList()
    val outputs = FXCollections.unmodifiableObservableList(out)

    private val tickPropInternal = SimpleIntegerProperty(0)
    val tickProp: IntegerExpression = ReadOnlyIntegerWrapper.integerExpression(tickPropInternal)
    var ticks by tickPropInternal
        private set

    private val animatingPropInternal = SimpleBooleanProperty(false)
    val animatingProp: BooleanExpression = ReadOnlyBooleanWrapper.booleanExpression(animatingPropInternal)
    var animating by animatingPropInternal
        private set

    private val playingPropInternal = SimpleBooleanProperty(false)
    val playingProp: BooleanExpression = ReadOnlyBooleanWrapper.booleanExpression(playingPropInternal)
    var playing by playingPropInternal
        private set

    private val stoppingPropInternal = SimpleBooleanProperty(false)
    val stoppingProp: BooleanExpression = ReadOnlyBooleanWrapper.booleanExpression(stoppingPropInternal)
    var stopping by stoppingPropInternal
        private set

    private val simulatingPropInternal = SimpleBooleanProperty(false)
    val simulatingProp: BooleanExpression = ReadOnlyBooleanWrapper.booleanExpression(simulatingPropInternal)
    var simulating by simulatingPropInternal
        private set

    val idleProp: BooleanExpression = animatingProp.not().and(playingProp.not()).and(simulatingProp.not())

    fun start(input: List<BigInteger?>) {
        out.clear()
        ticks = 0
        container.start(input)
        view.render()
    }

    fun quickTick() {
        val report = container.tick()
        view.render()
        ui {
            ticks++
            out.addAll(report.outputs)
        }
    }

    fun animatedTick(lengthMs: Long, onFinish: () -> Unit = {}) {
        animating = true
        val report = container.tick()
        view.animate(report.collisionReport, lengthMs)
        timer.schedule(lengthMs) {
            view.render()
            onFinish()
            animating = false
        }
        ui {
            out.addAll(report.outputs)
            ticks++
        }
    }

    fun simulate(ticks: Int) {
        simulating = true
        for(i in (1..ticks)){
            val report = container.tick()
            this.ticks++
            out.addAll(report.outputs)

            if(report.halted) break
        }

        view.render()
        simulating = false
    }

    fun reset() {
        container.reset()
        view.render()
    }

    fun clear() {
        container.clear()
        out.clear()
        ticks = 0
        view.render()
    }

    private fun recursiveAnimate(lengthMs: Long){
        animatedTick(lengthMs){
            if(container.running && !stopping) {
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

    fun stopPlaying(){
        stopping = true
    }
}
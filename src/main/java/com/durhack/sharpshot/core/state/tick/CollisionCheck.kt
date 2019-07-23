package com.durhack.sharpshot.core.state.tick

abstract class Checker(val bulletMovement: BulletMovement)

class SwapCheck(bulletMovement: BulletMovement) : Checker(bulletMovement) {
    private val c1 = bulletMovement.movement.from
    private val c2 = bulletMovement.movement.to

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SwapCheck
        if (c1 != other.c2) return false
        if (c2 != other.c1) return false
        return true
    }

    override fun hashCode(): Int = c1.hashCode() + c2.hashCode()
}

class FinalCheck(bulletMovement: BulletMovement) : Checker(bulletMovement) {
    private val to = bulletMovement.movement.to

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FinalCheck
        return to == other.to
    }

    override fun hashCode(): Int = to.hashCode()
}
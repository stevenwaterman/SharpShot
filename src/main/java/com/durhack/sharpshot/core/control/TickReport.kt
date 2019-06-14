package com.durhack.sharpshot.core.control

import java.math.BigInteger

data class TickReport(val collisionReport: CollisionReport, val outputs: List<BigInteger?>, val halted: Boolean)
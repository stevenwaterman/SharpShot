package com.durhack.sharpshot.core.nodes.routing.conditional

import java.math.BigInteger

class IfZeroNode : AbstractConditionalNode() {
    override fun branch(value: BigInteger?) = value?.signum() == 0
    override val type = "branch if zero"
    override val tooltip = "Redirects all zero bullets (=0). Other bullets pass through unaffected"
}

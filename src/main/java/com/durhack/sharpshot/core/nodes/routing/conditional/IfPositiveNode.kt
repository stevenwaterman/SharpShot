package com.durhack.sharpshot.core.nodes.routing.conditional

import java.math.BigInteger

class IfPositiveNode : AbstractConditionalNode() {
    override fun branch(value: BigInteger?) = value?.signum() == 1
    override val type = "branch if positive"
    override val tooltip = "Redirects all positive bullets (>0). Other bullets pass through unaffected"
}

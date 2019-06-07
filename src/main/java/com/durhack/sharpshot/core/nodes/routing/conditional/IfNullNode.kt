package com.durhack.sharpshot.core.nodes.routing.conditional

import java.math.BigInteger

class IfNullNode : AbstractConditionalNode() {
    override fun branch(value: BigInteger?) = value == null
    override val type = "branch if null"
    override val tooltip = "Redirects all empty/null bullets. Other bullets pass through unaffected"
}

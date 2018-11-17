package com.durhack.sharpshot.nodes;

public class NodeIf0 extends NodeIfCondition {
    @Override
    public boolean branchingCondition(int signum) {
        return (signum == 0);
    }
}

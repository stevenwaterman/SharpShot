package com.durhack.sharpshot.nodes;

public class NodeBranch extends NodeIfCondition {
    @Override
    public boolean branchingCondition(int signum) {
        return Boolean.TRUE;
    }
}

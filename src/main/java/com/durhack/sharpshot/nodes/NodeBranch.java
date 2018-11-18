package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class NodeBranch extends NodeConditional {
    @Override
    public boolean branchingCondition(int signum) {
        return Boolean.TRUE;
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(getRotation(), Color.web("#FF6699"), "");
    }

    @Override
    public String toString() {
        return "Branch";
    }
}

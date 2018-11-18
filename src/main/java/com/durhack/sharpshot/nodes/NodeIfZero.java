package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

public class NodeIfZero extends NodeConditional {
    @Override
    public boolean branchingCondition(int signum) {
        return (signum == 0);
    }

    @Override
    public String toString() {
        return "Branch if Zero";
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(getRotation(), Color.web("#FF0000"), "=0");
    }
}

package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

public class NodeIfPositive extends NodeConditional {
    @Override
    public boolean branchingCondition(int signum) {
        return (signum == 1);
    }

    @Override
    public String toString() {
        return "Branch if Positive";
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(getRotation(), Color.web("#FF9900"), ">0");
    }
}

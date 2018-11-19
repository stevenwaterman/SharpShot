package com.durhack.sharpshot.nodes.routing;

import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class IfZeroNode extends ConditionalNode {
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

package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

public class NodeIf0 extends NodeConditional {
    @Override
    public boolean branchingCondition(int signum) {
        return (signum == 0);
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(getRotation(), Color.GREEN, "0");
    }
}

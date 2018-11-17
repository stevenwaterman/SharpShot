package com.durhack.sharpshot.nodes;

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
        return new Rectangle(32.0, 32.0, Color.GREEN);
    }
}

package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
}

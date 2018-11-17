package com.durhack.sharpshot.nodes;

import com.sun.org.apache.bcel.internal.generic.Select;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
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
        Rectangle rectangle = new Rectangle(32.0, 32.0, Color.BLUE);
        Label label = new Label("A");
        return new StackPane(rectangle, label);
    }
}

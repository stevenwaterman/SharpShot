package com.durhack.sharpshot.nodes;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class NodeMult extends NodeArithmetic {

    @Override
    public BigInteger operation(BigInteger val1, BigInteger val2) {
        return val1.multiply(val2);
    }

    @Override
    public @NotNull Node toGraphic() {
        Rectangle rectangle = new Rectangle(32.0, 32.0, Color.LIGHTGREEN);
        Label label = new Label("X");
        return new StackPane(rectangle, label);
    }
}

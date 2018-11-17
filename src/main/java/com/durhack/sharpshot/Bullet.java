package com.durhack.sharpshot;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

/**
 * Immutable
 */
public class Bullet {
    @NotNull
    final private Direction direction;

    @Nullable
    final private BigInteger value;

    public Bullet(@NotNull Direction direction, @Nullable BigInteger value) {
        this.direction = direction;
        this.value = value;
    }

    @NotNull
    public Direction getDirection() {
        return direction;
    }

    @Nullable
    public BigInteger getValue() {
        return value;
    }

    @NotNull
    public Node toGraphic() {
        StackPane stackPane = new StackPane();

        Rectangle background = new Rectangle(16.0, 16.0, Color.WHEAT);
        stackPane.getChildren().add(background);

        if (value != null) {
            Label label = new Label(value.toString());
            stackPane.getChildren().add(label);
        }

        return stackPane;
    }
}

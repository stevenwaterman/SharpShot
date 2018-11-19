package com.durhack.sharpshot.nodes.math;

import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class SubNode extends MathNode {
    @Override
    public BigInteger operation(BigInteger val1, BigInteger val2) {
        return val1.subtract(val2);
    }

    @Override
    public String toString() {
        return "Subtract";
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(getRotation(), Color.web("#7700FF"), "-");
    }
}

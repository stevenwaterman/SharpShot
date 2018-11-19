package com.durhack.sharpshot.nodes.math;

import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class AddNode extends MathNode {
    @Override
    public BigInteger operation(BigInteger val1, BigInteger val2) {
        return val1.add(val2);
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(getRotation(), Color.web("#add8e6"), "+");
    }

    @Override
    public String toString() {
        return "Add";
    }
}

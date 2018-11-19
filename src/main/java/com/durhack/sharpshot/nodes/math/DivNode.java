package com.durhack.sharpshot.nodes.math;

import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class DivNode extends MathNode {
    @Override
    public BigInteger operation(@NotNull BigInteger val1, @NotNull BigInteger val2) {
        if(val2.equals(BigInteger.ZERO)){
            return null;
        }
        else{
            return val1.divide(val2);
        }
    }

    @Override
    public String toString() {
        return "Divide";
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(getRotation(), Color.web("#CC66FF"), "÷");
    }
}

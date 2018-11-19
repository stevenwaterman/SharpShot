package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class NodeDiv extends NodeArithmetic {
    @Override
    public BigInteger operation(BigInteger val1, BigInteger val2) {
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

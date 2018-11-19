package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ConstantNode implements INode {
    private Direction dir = Direction.UP;
    private BigInteger value;

    public ConstantNode(BigInteger value) {
        this.value = value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }

    @Override
    public @NotNull Direction getRotation() {
        return dir;
    }

    @Override
    public void rotateClockwise() {
        dir = Direction.clockwiseOf(dir);
    }

    @Override
    public @NotNull Map<Direction, BigInteger> run(@NotNull Bullet bullet) {
        // Shoot out a bullet
        HashMap<Direction, BigInteger> map = new HashMap<>();
        map.put(bullet.getDirection(), bullet.getValue());
        map.put(Direction.UP, getValue()); // if both same direction, only constant comes out
        return map;
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(getRotation(), Color.LIMEGREEN, value.toString());
    }

    public void reset() {}

    @Override
    public String toString() {
        return "Constant";
    }
}

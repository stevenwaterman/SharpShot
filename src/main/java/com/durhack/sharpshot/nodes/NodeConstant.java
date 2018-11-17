package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;

public class NodeConstant implements INode {
    private Direction dir = Direction.UP;
    private BigInteger value;

    public NodeConstant(BigInteger value) {
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
        map.put(Direction.UP, getValue());
        return map;
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Rectangle(32.0, 32.0, Color.GREEN);
    }
}

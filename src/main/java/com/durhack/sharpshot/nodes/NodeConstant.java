package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.gui.Triangle;
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
        if(bullet.getDirection() != Direction.UP){
            map.put(Direction.UP, getValue());
            map.put(bullet.getDirection(), bullet.getValue());
            return map;
        }
        return map;
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(getRotation(), Color.GREEN, value.toString());
    }
}

package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container implements INode {
    private Direction dir = Direction.UP;

    private INode[][] nodes;
    private List<Bullet> bullets;


    @Override
    public @NotNull Direction getRotation() {
        return dir;
    }

    @Override
    public void rotateClockwise() {
        dir = Direction.clockwiseOf(dir);
    }

    @NotNull
    @Override
    public Map<Direction, BigInteger> run(@NotNull Bullet bullet) {
        return new HashMap<>();
    }
}

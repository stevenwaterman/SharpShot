package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO how do we deal with the container returning multiple outputs over multiple ticks?

public class Container implements INode {
    private Direction dir = Direction.UP;

    @NotNull
    private INode[][] nodes;

    @NotNull
    private List<Bullet> bullets = new ArrayList<>();

    public Container(@NotNull INode[][] nodes) {
        this.nodes = nodes;
    }

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

    @NotNull
    public INode[][] getNodes() {
        return nodes;
    }

    @NotNull
    public List<Bullet> getBullets() {
        return bullets;
    }
}

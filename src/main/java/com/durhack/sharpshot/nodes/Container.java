package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO how do we deal with the container returning multiple outputs over multiple ticks?

public class Container implements INode {
    private Direction rotation = Direction.UP;

    @NotNull
    private Map<Coordinate, INode> nodes = new HashMap<>();

    @NotNull
    private Map<Coordinate, Bullet> bullets = new HashMap<>();

    public Container() {
    }

    @Override
    public @NotNull Direction getRotation() {
        return rotation;
    }

    @Override
    public void rotateClockwise() {
        rotation = Direction.clockwiseOf(rotation);
    }

    @NotNull
    @Override
    public Map<Direction, BigInteger> run(@NotNull Bullet bullet) {
        return new HashMap<>();
    }

    @NotNull
    public Map<Coordinate, INode> getNodes() {
        return nodes;
    }

    @NotNull
    public Map<Coordinate, Bullet> getBullets() {
        return bullets;
    }

    public void tick() {
        for (Map.Entry<Coordinate, Bullet> enrty: bullets.entrySet()) {
            Bullet bullet = enrty.getValue();
            Coordinate coordinate = enrty.getKey();
            //bullet.tick();
            //TODO check for bullets colliding / hitting nodes
        }
    }
}

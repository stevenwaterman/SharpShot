package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class NodeIn implements INode {
    private Direction dir = Direction.UP;
    @Override
    public @NotNull Direction getRotation() {
        return dir;
    }

    @Override
    public void rotateClockwise() {
        dir = Direction.clockwiseOf(dir);
    }

    //@Override
    public @NotNull Map<Direction, BigInteger> into(@NotNull Bullet bullet) {
        HashMap<Direction, BigInteger> map = new HashMap<>();
        map.put(bullet.getDirection(),bullet.getValue());
        return map;
    }
    @Override
    public @NotNull Map<Direction, BigInteger> run(@NotNull Bullet bullet) {
        return new HashMap<>();
    }
}

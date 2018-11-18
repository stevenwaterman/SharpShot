package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public abstract class NodeArithmetic implements INode {
    private Direction dir = Direction.UP;

    private Bullet mostRecentBullet = null;

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
        // Ignore null bullets
        if(bullet.getValue() == null)
            return new HashMap<>();

        if(mostRecentBullet == null) {
            // Nothing to add
            mostRecentBullet = bullet;
            return new HashMap<>();
        } else {
            HashMap<Direction, BigInteger> map = new HashMap<>();
            map.put(Direction.UP, operation(mostRecentBullet.getValue(),bullet.getValue()));
            mostRecentBullet = null;
            return map;
        }
    }

    protected abstract BigInteger operation(BigInteger val1, BigInteger val2);

    public void reset() {
        mostRecentBullet = null;
    }
}

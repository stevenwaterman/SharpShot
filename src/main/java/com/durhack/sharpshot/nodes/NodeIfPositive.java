package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class NodeIfPositive implements INode {
    private Direction dir = Direction.UP;

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
        else{
            HashMap<Direction, BigInteger> map = new HashMap<>();
            assert bullet.getValue() != null;
            if (bullet.getValue().signum() == 1) {
                map.put(Direction.UP, bullet.getValue());
                return map;
            }
            else{
                map.put(bullet.getDirection(), bullet.getValue());
                return map;
            }
        }
    }
}

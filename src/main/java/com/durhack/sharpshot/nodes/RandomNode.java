package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomNode implements INode {
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
    public String toString() {
        return "Random";
    }

    private BigInteger rand(BigInteger n) {
        int sign = 1;
        if (n.compareTo(BigInteger.ZERO) == 0){
            return BigInteger.ZERO;}
        if (n.compareTo(BigInteger.ZERO) < 0){
            sign = -1;}
        n = n.abs();
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while( result.compareTo(n) >= 0 ) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result.multiply(new BigInteger(sign + ""));
    }
    @Override
    public @NotNull Map<Direction, BigInteger> run(@NotNull Bullet bullet) {
        HashMap<Direction, BigInteger> map = new HashMap<>();
        if (bullet.getValue() != null) {
            map.put(dir, rand(bullet.getValue()));
        }
        return map;
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Rectangle(32.0, 32.0, Color.GREEN);
    }

    @Override
    public void reset() {
        //nothing to do
    }
}

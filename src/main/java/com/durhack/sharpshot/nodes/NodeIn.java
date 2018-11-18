package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeIn extends AbstractNodeInput {
    private Direction dir = Direction.UP;
    private final int index;
    private BigInteger input = null;

    public NodeIn(int index) {
        this.index = index;
    }

    @Override
    public @NotNull Direction getRotation() {
        return dir;
    }

    @Override
    public void rotateClockwise() {
        dir = Direction.clockwiseOf(dir);
    }

    public @NotNull Map<Direction, BigInteger> input(@NotNull List<BigInteger> inputs) {
        HashMap<Direction, BigInteger> map = new HashMap<>();
        if (index < inputs.size()) {
            input = inputs.get(index);
            map.put(dir, input);
        }
        return map;
    }

    @Override
    public String toString() {
        return "Input";
    }

    @Override
    public @NotNull Map<Direction, BigInteger> run(@NotNull Bullet bullet) {
        Map<Direction, BigInteger> bullets = new HashMap<>();
        bullets.put(bullet.getDirection(), bullet.getValue());
        if (input != null) {
            bullets.put(Direction.UP, input);
        }
        return bullets;
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(getRotation(), Color.web("#FFFF00"), "IN" + index);
    }

    public void reset() {   }

    public int getIndex() {
        return index;
    }
}

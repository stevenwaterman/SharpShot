package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeList extends AbstractNodeInput {
    private Direction dir = Direction.UP;
    private List<BigInteger> inputs;
    private int nextOutputIndex = 0;

    public NodeList() {

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
    public String toString() {
        return "Argument List";
    }

    @Override
    public @NotNull Map<Direction, BigInteger> run(@NotNull Bullet bullet) {
        HashMap<Direction, BigInteger> bullets = new HashMap<>();
        bullets.put(bullet.getDirection(), bullet.getValue());
        if (nextOutputIndex < inputs.size()) {
            bullets.put(Direction.UP, inputs.get(nextOutputIndex));
            nextOutputIndex++;
        }
        return bullets;
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(getRotation(), Color.web("#FFFF00"), "LST");
    }

    public void reset() {
        inputs = new ArrayList<>();
        nextOutputIndex = 0;
    }

    @Override
    public @NotNull Map<Direction, BigInteger> input(@NotNull List<BigInteger> inputs) {
        this.inputs = inputs;
        return new HashMap<>();
    }
}

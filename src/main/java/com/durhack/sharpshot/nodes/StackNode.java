package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.gui.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StackNode implements INode {
    private Direction rotation = Direction.UP;
    private Stack<BigInteger> stack = new Stack<>();

    @Override
    public @NotNull Direction getRotation() {
        return rotation;
    }

    @Override
    public void rotateClockwise() {
        rotation = rotation.clockwise();
    }

    @Override
    public @NotNull Map<Direction, BigInteger> run(@NotNull Bullet bullet) {
        Map<Direction, BigInteger> bullets = new HashMap<>();
        if (bullet.getDirection() == Direction.UP) {
            if(!stack.isEmpty()){
                bullets.put(Direction.UP, stack.pop());
            }
        } else {
            stack.add(bullet.getValue());
        }
        return bullets;
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Triangle(rotation, Color.web("#FFFF00"), "S" + stack.size());
    }

    @Override
    public void reset() {
        stack.clear();
    }

    @Override
    public String toString() {
        return "Stack";
    }
}

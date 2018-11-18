package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.gui.Diamond;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class NodeSplitter implements INode {
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
        // Shoot out 3 bullets in other directions
        HashMap<Direction, BigInteger> map = new HashMap<>();
        for(Direction d : Direction.others(Direction.inverseOf(bullet.getDirection())))
            map.put(d, bullet.getValue());
        return map;
    }

    @Override
    public String toString() {
        return "Splitter";
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Diamond(getRotation(), Color.YELLOW, "Y");
    }

    public void reset() {}
}

package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class NodeAscii implements INode {
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
        BigInteger value = bullet.getValue();
        if(value != null) {
            System.out.println(findAscii(value + ""));
        }
        return new HashMap<>();
    }
    public String findAscii (String string){
        while (string.length() % 3 != 0)
    {
        string = '0' + string;
    }
    String result = "";
    for (int i = 0; i < string.length(); i += 3)
    {
        result += (char)(Integer.parseInt(string.substring(i, i + 3)));}
    return result;}

        @Override
    public @NotNull Node toGraphic() {
        Rectangle rectangle = new Rectangle(32.0, 32.0, Color.SANDYBROWN);
        Label label = new Label("OUT");
        return new StackPane(rectangle, label);
    }

    public void reset() {}
}

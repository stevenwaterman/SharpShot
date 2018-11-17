package com.durhack.sharpshot;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

import javax.xml.ws.soap.MTOM;
import java.math.BigInteger;
import java.util.Map;

public interface INode {
    @NotNull
    Direction getRotation();

    void rotateClockwise();

    @NotNull
    Map<Direction, BigInteger> run(@NotNull Bullet bullet);

    @NotNull Node toGraphic();
}
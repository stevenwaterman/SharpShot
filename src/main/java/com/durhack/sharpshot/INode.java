package com.durhack.sharpshot;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Map;

public interface INode {
    @NotNull
    Direction getRotation();

    void rotateClockwise();

    @NotNull
    Map<Direction, BigInteger> run(@NotNull Bullet bullet);
}
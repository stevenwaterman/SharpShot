package com.durhack.sharpshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
    UP(0,-1),
    RIGHT(1,0),
    DOWN(0,1),
    LEFT(-1,0);

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    private int deltaX;
    private int deltaY;

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    @NotNull
    public static Direction clockwiseOf(@NotNull Direction d) {
        switch(d) {
            case UP:    return Direction.RIGHT;
            case RIGHT: return Direction.DOWN;
            case DOWN:  return Direction.LEFT;
            case LEFT:  return Direction.UP;
        }

        throw new RuntimeException("Cannot find clockwise of " + d.toString());
    }

    public static Direction inverseOf(@NotNull Direction d) {
        return Direction.clockwiseOf(Direction.clockwiseOf(d));
    }

    @NotNull
    public static List<Direction> others(@NotNull Direction d) {
        List<Direction> dirs = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            d = Direction.clockwiseOf(d);
            dirs.add(d);
        }

        return dirs;
    }

    public Direction antiClockwise() {
        return Direction.clockwiseOf(Direction.clockwiseOf(Direction.clockwiseOf(this)));
    }

    public Direction clockwise() {
        return Direction.clockwiseOf(this);
    }
}
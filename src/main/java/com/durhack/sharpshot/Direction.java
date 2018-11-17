package com.durhack.sharpshot;

import org.jetbrains.annotations.NotNull;

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
}
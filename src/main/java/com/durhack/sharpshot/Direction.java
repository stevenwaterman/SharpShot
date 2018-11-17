package com.durhack.sharpshot;

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
}
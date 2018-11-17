package com.durhack.sharpshot;


/**
 * This is an immutable class.
 */
public class Coordinate {
    final private int x;
    final private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate plus(Direction direction){
        int newX = x + direction.getDeltaX();
        int newY = y + direction.getDeltaY();
        return new Coordinate(newX, newY);
    }
}

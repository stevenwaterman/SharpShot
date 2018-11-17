package com.durhack.sharpshot;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Coordinate wrap(int width, int height) {
        return new Coordinate((x+width) % width, (y+height) % height);
    }
}

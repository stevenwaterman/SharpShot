package com.durhack.sharpshot;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class Bullet {
    @NotNull
    private Direction direction;

    private int x;
    private int y;

    @NotNull
    private BigInteger value;

    public Bullet(@NotNull Direction direction, int x, int y, @NotNull BigInteger value) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.value = value;
    }

    @NotNull
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(@NotNull Direction direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @NotNull
    public BigInteger getValue() {
        return value;
    }

    public void setValue(@NotNull BigInteger value) {
        this.value = value;
    }
}

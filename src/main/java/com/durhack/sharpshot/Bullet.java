package com.durhack.sharpshot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

public class Bullet {
    @NotNull
    private Direction direction;

    @Nullable
    private BigInteger value;

    public Bullet(@NotNull Direction direction, @NotNull BigInteger value) {
        this.direction = direction;
        this.value = value;
    }

    @NotNull
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(@NotNull Direction direction) {
        this.direction = direction;
    }

    @Nullable
    public BigInteger getValue() {
        return value;
    }

    public void setValue(@NotNull BigInteger value) {
        this.value = value;
    }
}

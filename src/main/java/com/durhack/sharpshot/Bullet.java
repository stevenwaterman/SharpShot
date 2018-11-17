package com.durhack.sharpshot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

/**
 * Immutable
 */
public class Bullet {
    @NotNull
    final private Direction direction;

    @Nullable
    final private BigInteger value;

    public Bullet(@NotNull Direction direction, @Nullable BigInteger value) {
        this.direction = direction;
        this.value = value;
    }

    @NotNull
    public Direction getDirection() {
        return direction;
    }

    @Nullable
    public BigInteger getValue() {
        return value;
    }
}

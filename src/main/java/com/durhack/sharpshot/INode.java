package com.durhack.sharpshot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface INode {
    @Nullable
    Bullet run(@NotNull Bullet bullet);
}
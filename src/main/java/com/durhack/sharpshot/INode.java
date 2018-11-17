package com.durhack.sharpshot;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface INode {
    @NotNull
    List<Bullet> run(@NotNull Bullet bullet);
}
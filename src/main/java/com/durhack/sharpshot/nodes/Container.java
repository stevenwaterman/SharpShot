package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.INode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Container implements INode {
    private INode[][] nodes;
    private List<Bullet> bullets;


    @NotNull
    @Override
    public List<Bullet> run(@NotNull Bullet bullet) {
        return new ArrayList<>();
    }
}

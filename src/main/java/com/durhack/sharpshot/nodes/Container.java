package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.INode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Container implements INode {
    @NotNull
    private INode[][] nodes;

    @NotNull
    private List<Bullet> bullets = new ArrayList<>();

    public Container(@NotNull INode[][] nodes) {
        this.nodes = nodes;
    }

    @NotNull
    @Override
    public List<Bullet> run(@NotNull Bullet bullet) {
        return new ArrayList<>();
    }

    @NotNull
    public INode[][] getNodes() {
        return nodes;
    }

    @NotNull
    public List<Bullet> getBullets() {
        return bullets;
    }
}

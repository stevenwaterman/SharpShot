package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public abstract class AbstractNodeInput implements INode {
    abstract public @NotNull Map<Direction, BigInteger> input(@NotNull List<BigInteger> inputs);
}

package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;

public class NodeAdd extends NodeArithmetic {
    public BigInteger operation(BigInteger val1, BigInteger val2){
        return val1.add(val2);}
}

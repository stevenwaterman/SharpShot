package com.durhack.sharpshot.nodes;

import java.math.BigInteger;

public class NodeDiv extends NodeArithmetic {
    @Override
    public BigInteger operation(BigInteger val1, BigInteger val2) {
        return val1.divide(val2);
    }
}

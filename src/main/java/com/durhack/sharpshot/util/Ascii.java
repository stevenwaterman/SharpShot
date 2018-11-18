package com.durhack.sharpshot.util;

import java.math.BigInteger;

public class Ascii {
    public static BigInteger toBig(char ascii){
        int val = ascii;
        return new BigInteger(val + "");
    }

    public static char fromBig (BigInteger bigint){
        String string = bigint.mod(new BigInteger("255")) + "";
       return (char)(Integer.parseInt(string));
    }
}

package com.durhack.sharpshot.util;

import com.durhack.sharpshot.Coordinate;

public class Movement {
    private final Coordinate from;
    private final Coordinate to;

    public Coordinate getFrom() {
        return from;
    }

    public Coordinate getTo() {
        return to;
    }

    public Movement(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
    }
}

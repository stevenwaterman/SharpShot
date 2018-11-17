package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Coordinate;

public class Movement {
    private Coordinate from;
    private Coordinate to;

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

package com.netsensia.rivalchess.model;

public enum MoveDirection {
    N (0,-1),
    NE (1,-1),
    E (1,0),
    SE (1,1),
    S (0,1),
    SW (-1,1),
    W (-1,0),
    NW (-1,-1)
    ;

    private final int xIncrement;
    private final int yIncrement;

    private MoveDirection(final int xIncrement, final int yIncrement) {
        this.xIncrement = xIncrement;
        this.yIncrement = yIncrement;

    }

    public int getXIncrement() {
        return this.xIncrement;
    }

    public int getYIncrement() {
        return this.yIncrement;
    }
}

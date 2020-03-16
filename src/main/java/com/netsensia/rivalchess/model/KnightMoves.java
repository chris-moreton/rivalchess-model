package com.netsensia.rivalchess.model;

public enum KnightMoves {
    NE (1,-2),
    NW (-1,-2),
    EN (2,-1),
    ES (2,1),
    SE (1,2),
    SW (-1,2),
    WN (-2,-1),
    WS (-2,1)
    ;

    private final int xIncrement;
    private final int yIncrement;

    private KnightMoves(final int xIncrement, final int yIncrement) {
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

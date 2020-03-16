package com.netsensia.rivalchess.model;

public class PawnMoveHelper {

    private PawnMoveHelper() { }

    public static int homeRank(Colour colour) {
        return colour == Colour.WHITE ? 6 : 1;
    }

    public static int enPassantFromRank(Colour colour) {
        return colour == Colour.WHITE ? 3 : 4;
    }

    public static int enPassantToRank(Colour colour) {
        return colour == Colour.WHITE ? 2 : 5;
    }

    public static int promotionRank(Colour colour) {
        return colour == Colour.WHITE ? 1 : 6;
    }

    public static int advanceDirection(Colour colour) {
        return colour == Colour.WHITE ? -1 : 1;
    }
}

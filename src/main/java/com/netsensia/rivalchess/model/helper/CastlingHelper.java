package com.netsensia.rivalchess.model.helper;

import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.Square;

public class CastlingHelper {

    private CastlingHelper() { }

    public static Square queenRookHome(Colour colour) {
        return Square.fromCoords(0, colour == Colour.WHITE ? 7 : 0);
    }

    public static Square queenKnightHome(Colour colour) {
        return Square.fromCoords(1, colour == Colour.WHITE ? 7 : 0);
    }

    public static Square queenBishopHome(Colour colour) {
        return Square.fromCoords(2, colour == Colour.WHITE ? 7 : 0);
    }

    public static Square queenHome(Colour colour) {
        return Square.fromCoords(3, colour == Colour.WHITE ? 7 : 0);
    }

    public static Square kingHome(Colour colour) {
        return Square.fromCoords(4, colour == Colour.WHITE ? 7 : 0);
    }

    public static Square kingBishopHome(Colour colour) {
        return Square.fromCoords(5, colour == Colour.WHITE ? 7 : 0);
    }

    public static Square kingKnightHome(Colour colour) {
        return Square.fromCoords(6, colour == Colour.WHITE ? 7 : 0);
    }

    public static Square kingRookHome(Colour colour) {
        return Square.fromCoords(7, colour == Colour.WHITE ? 7 : 0);
    }

}

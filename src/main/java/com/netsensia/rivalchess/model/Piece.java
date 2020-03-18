package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.exception.BadPieceConversionException;

public enum Piece {
    NONE (0),
    PAWN  (1),
    KNIGHT(2),
    ROOK   (3),
    KING (4),
    QUEEN (5),
    BISHOP (6)
    ;

    private int index;

    private Piece(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public SquareOccupant toSquareOccupant(final Colour colour) {
        switch (this) {
            case PAWN: return colour == Colour.WHITE ? SquareOccupant.WP : SquareOccupant.BP;
            case KNIGHT: return colour == Colour.WHITE ? SquareOccupant.WN : SquareOccupant.BN;
            case BISHOP: return colour == Colour.WHITE ? SquareOccupant.WB : SquareOccupant.BB;
            case ROOK: return colour == Colour.WHITE ? SquareOccupant.WR : SquareOccupant.BR;
            case QUEEN: return colour == Colour.WHITE ? SquareOccupant.WQ : SquareOccupant.BQ;
            case KING: return colour == Colour.WHITE ? SquareOccupant.WK : SquareOccupant.BK;
            default:
                throw new BadPieceConversionException("Can't create squareOccupant from " + this + " and " + colour);
        }
    }

    public static Piece fromSquareOccupant(final SquareOccupant squareOccupant) {
        switch (squareOccupant) {
            case WP:
            case BP:
                return Piece.PAWN;
            case WR:
            case BR:
                return Piece.ROOK;
            case WB:
            case BB:
                return Piece.BISHOP;
            case WN:
            case BN:
                return Piece.KNIGHT;
            case WQ:
            case BQ:
                return Piece.QUEEN;
            case WK:
            case BK:
                return Piece.KING;
            default:
                return Piece.NONE;
        }
    }

}
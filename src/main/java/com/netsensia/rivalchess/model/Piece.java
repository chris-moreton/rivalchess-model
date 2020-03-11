package com.netsensia.rivalchess.model;

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

    public static Piece fromSquareOccupant(SquareOccupant squareOccupant) {
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
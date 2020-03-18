package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.exception.EnumConversionException;

public enum SquareOccupant {
    NONE(-1),
    WP(0),
    WN(1),
    WB(2),
    WQ(3),
    WK(4),
    WR(5),
    BP(6),
    BN(7),
    BB(8),
    BQ(9),
    BK(10),
    BR(11)
    ;

    private int index;

    private SquareOccupant(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public SquareOccupant ofColour(Colour colour) {
        if (this == SquareOccupant.NONE) {
            return SquareOccupant.NONE;
        }

        return colour == Colour.WHITE
                ? SquareOccupant.fromIndex(index % 6)
                : SquareOccupant.fromIndex(index % 6 + 6);
    }

    private static SquareOccupant fromIndex(int index){
        for(SquareOccupant cp : SquareOccupant.values()){
            if(cp.index == index) {
                return cp;
            }
        }
        return SquareOccupant.NONE;
    }

    public Colour getColour() {
        if (index == SquareOccupant.NONE.getIndex()) {
            throw new EnumConversionException("Can't get piece colour of an empty square");
        }

        return index <= SquareOccupant.WR.getIndex() ? Colour.WHITE : Colour.BLACK;
    }

    public Piece getPiece() {
        switch (this) {
            case NONE:
                return Piece.NONE;
            case WP:
            case BP:
                return Piece.PAWN;
            case WK:
            case BK:
                return Piece.KING;
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
            default:
                throw new EnumConversionException("Invalid SquareOccupant");
        }
    }

    public char toChar() {
        switch (this) {
            case NONE:
                return '-';
            case WP:
                return 'P';
            case BP:
                return 'p';
            case WK:
                return 'K';
            case BK:
                return 'k';
            case WR:
                return 'R';
            case BR:
                return 'r';
            case WB:
                return 'B';
            case BB:
                return 'b';
            case WN:
                return 'N';
            case BN:
                return 'n';
            case WQ:
                return 'Q';
            case BQ:
                return 'q';
            default:
                throw new EnumConversionException("Invalid SquareOccupant");
        }
    }

    public static SquareOccupant fromChar(char piece){
        switch (piece) {
            case 'p':
                return SquareOccupant.BP;
            case 'q':
                return SquareOccupant.BQ;
            case 'r':
                return SquareOccupant.BR;
            case 'n':
                return SquareOccupant.BN;
            case 'b':
                return SquareOccupant.BB;
            case 'k':
                return SquareOccupant.BK;
            case 'P':
                return SquareOccupant.WP;
            case 'Q':
                return SquareOccupant.WQ;
            case 'R':
                return SquareOccupant.WR;
            case 'N':
                return SquareOccupant.WN;
            case 'B':
                return SquareOccupant.WB;
            case 'K':
                return SquareOccupant.WK;
            default:
                return SquareOccupant.NONE;
        }
    }

}
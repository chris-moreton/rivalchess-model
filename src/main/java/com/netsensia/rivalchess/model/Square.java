package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.exception.InvalidAlgebraicSquareException;

public enum Square {

    A8 (0,0),B8 (1,0),C8 (2,0),D8 (3,0),E8 (4,0),F8 (5,0),G8 (6,0),H8 (7,0),
    A7 (0,1),B7 (1,1),C7 (2,1),D7 (3,1),E7 (4,1),F7 (5,1),G7 (6,1),H7 (7,1),
    A6 (0,2),B6 (1,2),C6 (2,2),D6 (3,2),E6 (4,2),F6 (5,2),G6 (6,2),H6 (7,2),
    A5 (0,3),B5 (1,3),C5 (2,3),D5 (3,3),E5 (4,3),F5 (5,3),G5 (6,3),H5 (7,3),
    A4 (0,4),B4 (1,4),C4 (2,4),D4 (3,4),E4 (4,4),F4 (5,4),G4 (6,4),H4 (7,4),
    A3 (0,5),B3 (1,5),C3 (2,5),D3 (3,5),E3 (4,5),F3 (5,5),G3 (6,5),H3 (7,5),
    A2 (0,6),B2 (1,6),C2 (2,6),D2 (3,6),E2 (4,6),F2 (5,6),G2 (6,6),H2 (7,6),
    A1 (0,7),B1 (1,7),C1 (2,7),D1 (3,7),E1 (4,7),F1 (5,7),G1 (6,7),H1 (7,7),
    ;

    private final int xFile;
	private final int yRank;

    private Square(final int xFile, final int yRank) {
        this.xFile = xFile;
        this.yRank = yRank;
    }

    public static Square fromCoords(final int x, final int y) {
        return Square.valueOf("" + (char) (65 + x) + Character.forDigit((8 - y), 10));
    }

    public static Square fromAlgebraic(String algebraic) {
        if (algebraic.length() != 2) {
            throw new InvalidAlgebraicSquareException("Invalid algebraic square " + algebraic);
        }

        final int x = algebraic.toCharArray()[0] - 97;
        final int y = 8 - (algebraic.toCharArray()[1] - 48);

        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new InvalidAlgebraicSquareException("Invalid algebraic square " + algebraic);
        }

        return fromCoords(x,y);
    }

    public int getXFile() {
        return this.xFile;
    }

    public int getYRank() {
        return this.yRank;
    }

    public char getAlgebraicXFile() {
        return (char) (97 + this.getXFile());
    }

    public char getAlgebraicYRank() {
        return Character.forDigit((8 - this.getYRank()), 10);
    }

    public String getAlgebraic() {
        return "" + this.getAlgebraicXFile() + getAlgebraicYRank();
    }

}

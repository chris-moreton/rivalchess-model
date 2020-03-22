package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.exception.EnumConversionException;
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
        switch (y * 8 + x) {
            case 0: return Square.A8;
            case 1: return Square.B8;
            case 2: return Square.C8;
            case 3: return Square.D8;
            case 4: return Square.E8;
            case 5: return Square.F8;
            case 6: return Square.G8;
            case 7: return Square.H8;
            case 8: return Square.A7;
            case 9: return Square.B7;
            case 10: return Square.C7;
            case 11: return Square.D7;
            case 12: return Square.E7;
            case 13: return Square.F7;
            case 14: return Square.G7;
            case 15: return Square.H7;
            case 16: return Square.A6;
            case 17: return Square.B6;
            case 18: return Square.C6;
            case 19: return Square.D6;
            case 20: return Square.E6;
            case 21: return Square.F6;
            case 22: return Square.G6;
            case 23: return Square.H6;
            case 24: return Square.A5;
            case 25: return Square.B5;
            case 26: return Square.C5;
            case 27: return Square.D5;
            case 28: return Square.E5;
            case 29: return Square.F5;
            case 30: return Square.G5;
            case 31: return Square.H5;
            case 32: return Square.A4;
            case 33: return Square.B4;
            case 34: return Square.C4;
            case 35: return Square.D4;
            case 36: return Square.E4;
            case 37: return Square.F4;
            case 38: return Square.G4;
            case 39: return Square.H4;
            case 40: return Square.A3;
            case 41: return Square.B3;
            case 42: return Square.C3;
            case 43: return Square.D3;
            case 44: return Square.E3;
            case 45: return Square.F3;
            case 46: return Square.G3;
            case 47: return Square.H3;
            case 48: return Square.A2;
            case 49: return Square.B2;
            case 50: return Square.C2;
            case 51: return Square.D2;
            case 52: return Square.E2;
            case 53: return Square.F2;
            case 54: return Square.G2;
            case 55: return Square.H2;
            case 56: return Square.A1;
            case 57: return Square.B1;
            case 58: return Square.C1;
            case 59: return Square.D1;
            case 60: return Square.E1;
            case 61: return Square.F1;
            case 62: return Square.G1;
            case 63: return Square.H1;
            default:
                throw new EnumConversionException("Invalid Square conversion");
        }
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

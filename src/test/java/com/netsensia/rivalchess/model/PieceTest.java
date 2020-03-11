package com.netsensia.rivalchess.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PieceTest {

    @Test
    public void testConvertFromSquareOccupant() {
        assertEquals(Piece.BISHOP, Piece.fromSquareOccupant(SquareOccupant.WB));
        assertEquals(Piece.BISHOP, Piece.fromSquareOccupant(SquareOccupant.BB));
        assertEquals(Piece.KNIGHT, Piece.fromSquareOccupant(SquareOccupant.WN));
        assertEquals(Piece.KNIGHT, Piece.fromSquareOccupant(SquareOccupant.BN));
        assertEquals(Piece.ROOK, Piece.fromSquareOccupant(SquareOccupant.WR));
        assertEquals(Piece.ROOK, Piece.fromSquareOccupant(SquareOccupant.BR));
        assertEquals(Piece.QUEEN, Piece.fromSquareOccupant(SquareOccupant.WQ));
        assertEquals(Piece.QUEEN, Piece.fromSquareOccupant(SquareOccupant.BQ));
        assertEquals(Piece.KING, Piece.fromSquareOccupant(SquareOccupant.WK));
        assertEquals(Piece.KING, Piece.fromSquareOccupant(SquareOccupant.BK));
        assertEquals(Piece.PAWN, Piece.fromSquareOccupant(SquareOccupant.WP));
        assertEquals(Piece.PAWN, Piece.fromSquareOccupant(SquareOccupant.BP));
        assertEquals(Piece.NONE, Piece.fromSquareOccupant(SquareOccupant.NONE));

    }
}
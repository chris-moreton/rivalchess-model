package com.netsensia.rivalchess.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class SquareOccupantTest {

    @Test
    public void testOfColour() {
        assertEquals(SquareOccupant.WP, SquareOccupant.WP.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.WP, SquareOccupant.BP.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.BP, SquareOccupant.WP.ofColour(Colour.BLACK));
        assertEquals(SquareOccupant.BP, SquareOccupant.BP.ofColour(Colour.BLACK));

        assertEquals(SquareOccupant.WN, SquareOccupant.WN.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.WN, SquareOccupant.BN.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.BN, SquareOccupant.WN.ofColour(Colour.BLACK));
        assertEquals(SquareOccupant.BN, SquareOccupant.BN.ofColour(Colour.BLACK));

        assertEquals(SquareOccupant.WB, SquareOccupant.WB.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.WB, SquareOccupant.BB.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.BB, SquareOccupant.WB.ofColour(Colour.BLACK));
        assertEquals(SquareOccupant.BB, SquareOccupant.BB.ofColour(Colour.BLACK));

        assertEquals(SquareOccupant.WR, SquareOccupant.WR.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.WR, SquareOccupant.BR.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.BR, SquareOccupant.WR.ofColour(Colour.BLACK));
        assertEquals(SquareOccupant.BR, SquareOccupant.BR.ofColour(Colour.BLACK));

        assertEquals(SquareOccupant.WQ, SquareOccupant.WQ.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.WQ, SquareOccupant.BQ.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.BQ, SquareOccupant.WQ.ofColour(Colour.BLACK));
        assertEquals(SquareOccupant.BQ, SquareOccupant.BQ.ofColour(Colour.BLACK));

        assertEquals(SquareOccupant.WK, SquareOccupant.WK.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.WK, SquareOccupant.BK.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.BK, SquareOccupant.WK.ofColour(Colour.BLACK));
        assertEquals(SquareOccupant.BK, SquareOccupant.BK.ofColour(Colour.BLACK));

        assertEquals(SquareOccupant.NONE, SquareOccupant.NONE.ofColour(Colour.WHITE));
        assertEquals(SquareOccupant.NONE, SquareOccupant.NONE.ofColour(Colour.BLACK));
    }

    @Test
    public void testGetColour() {
        assertEquals(Colour.WHITE, SquareOccupant.WP.getColour());
        assertEquals(Colour.WHITE, SquareOccupant.WN.getColour());
        assertEquals(Colour.WHITE, SquareOccupant.WB.getColour());
        assertEquals(Colour.WHITE, SquareOccupant.WR.getColour());
        assertEquals(Colour.WHITE, SquareOccupant.WQ.getColour());
        assertEquals(Colour.WHITE, SquareOccupant.WK.getColour());

        assertEquals(Colour.BLACK, SquareOccupant.BP.getColour());
        assertEquals(Colour.BLACK, SquareOccupant.BN.getColour());
        assertEquals(Colour.BLACK, SquareOccupant.BB.getColour());
        assertEquals(Colour.BLACK, SquareOccupant.BR.getColour());
        assertEquals(Colour.BLACK, SquareOccupant.BQ.getColour());
        assertEquals(Colour.BLACK, SquareOccupant.BK.getColour());
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testGetColourOfEmptySquare() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't get piece colour of an empty square");
        SquareOccupant.NONE.getColour();
    }

    @Test
    public void testGetPiece() {
        assertEquals(Piece.PAWN, SquareOccupant.WP.getPiece());
        assertEquals(Piece.KNIGHT, SquareOccupant.WN.getPiece());
        assertEquals(Piece.BISHOP, SquareOccupant.WB.getPiece());
        assertEquals(Piece.ROOK, SquareOccupant.WR.getPiece());
        assertEquals(Piece.QUEEN, SquareOccupant.WQ.getPiece());
        assertEquals(Piece.KING, SquareOccupant.WK.getPiece());

        assertEquals(Piece.PAWN, SquareOccupant.BP.getPiece());
        assertEquals(Piece.KNIGHT, SquareOccupant.BN.getPiece());
        assertEquals(Piece.BISHOP, SquareOccupant.BB.getPiece());
        assertEquals(Piece.ROOK, SquareOccupant.BR.getPiece());
        assertEquals(Piece.QUEEN, SquareOccupant.BQ.getPiece());
        assertEquals(Piece.KING, SquareOccupant.BK.getPiece());

        assertEquals(Piece.NONE, SquareOccupant.NONE.getPiece());

    }

    @Test
    public void testGetChar() {
        assertEquals('P', SquareOccupant.WP.toChar());
        assertEquals('N', SquareOccupant.WN.toChar());
        assertEquals('B', SquareOccupant.WB.toChar());
        assertEquals('R', SquareOccupant.WR.toChar());
        assertEquals('Q', SquareOccupant.WQ.toChar());
        assertEquals('K', SquareOccupant.WK.toChar());

        assertEquals('p', SquareOccupant.BP.toChar());
        assertEquals('n', SquareOccupant.BN.toChar());
        assertEquals('b', SquareOccupant.BB.toChar());
        assertEquals('r', SquareOccupant.BR.toChar());
        assertEquals('q', SquareOccupant.BQ.toChar());
        assertEquals('k', SquareOccupant.BK.toChar());

        assertEquals('-', SquareOccupant.NONE.toChar());

    }

    @Test
    public void testFromChar() {
        assertEquals(SquareOccupant.WP, SquareOccupant.fromChar('P'));
        assertEquals(SquareOccupant.WN, SquareOccupant.fromChar('N'));
        assertEquals(SquareOccupant.WB, SquareOccupant.fromChar('B'));
        assertEquals(SquareOccupant.WR, SquareOccupant.fromChar('R'));
        assertEquals(SquareOccupant.WQ, SquareOccupant.fromChar('Q'));
        assertEquals(SquareOccupant.WK, SquareOccupant.fromChar('K'));

        assertEquals(SquareOccupant.BP, SquareOccupant.fromChar('p'));
        assertEquals(SquareOccupant.BN, SquareOccupant.fromChar('n'));
        assertEquals(SquareOccupant.BB, SquareOccupant.fromChar('b'));
        assertEquals(SquareOccupant.BR, SquareOccupant.fromChar('r'));
        assertEquals(SquareOccupant.BQ, SquareOccupant.fromChar('q'));
        assertEquals(SquareOccupant.BK, SquareOccupant.fromChar('k'));

        assertEquals(SquareOccupant.NONE, SquareOccupant.fromChar('-'));
        assertEquals(SquareOccupant.NONE, SquareOccupant.fromChar(' '));
        assertEquals(SquareOccupant.NONE, SquareOccupant.fromChar('x'));
        assertEquals(SquareOccupant.NONE, SquareOccupant.fromChar('0'));
        assertEquals(SquareOccupant.NONE, SquareOccupant.fromChar('@'));
        assertEquals(SquareOccupant.NONE, SquareOccupant.fromChar(':'));

    }
}
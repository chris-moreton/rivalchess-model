package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.util.BoardUtils;
import com.netsensia.rivalchess.model.util.CommonTestUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    public void testSetSquareOccupant() {
        Board.BoardBuilder boardBuilder = new Board.BoardBuilder();
        boardBuilder.withSquareOccupant(Square.fromCoords(1,7), SquareOccupant.BB);
        assertEquals(SquareOccupant.BB, boardBuilder.build().getSquareOccupant(Square.fromCoords(1, 7)));
    }

    @Test
    public void testGetPieceSquares() {

        final List<Square> expectedSquares =
                new ArrayList<>(Arrays.asList(
                        Square.fromCoords(1,0),
                        Square.fromCoords(6, 0)
                ));

        final List<Square> actualSquares = BoardUtils.getSquaresWithOccupant(
                CommonTestUtils.getStartBoard(), SquareOccupant.BN);

        Collections.sort(expectedSquares);
        Collections.sort(actualSquares);

        assertEquals(expectedSquares, actualSquares);
    }

    @Test
    public void testCastleFlags() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        assertTrue(boardBuilder.build().isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(boardBuilder.build().isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(boardBuilder.build().isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(boardBuilder.build().isQueenSideCastleAvailable(Colour.BLACK));

        boardBuilder.withIsKingSideCastleAvailable(Colour.WHITE, false);
        assertFalse(boardBuilder.build().isKingSideCastleAvailable(Colour.WHITE));

        boardBuilder.withIsKingSideCastleAvailable(Colour.WHITE, true);
        assertTrue(boardBuilder.build().isKingSideCastleAvailable(Colour.WHITE));

        boardBuilder.withIsKingSideCastleAvailable(Colour.BLACK, false);
        assertFalse(boardBuilder.build().isKingSideCastleAvailable(Colour.BLACK));

        boardBuilder.withIsKingSideCastleAvailable(Colour.BLACK, true);
        assertTrue(boardBuilder.build().isKingSideCastleAvailable(Colour.BLACK));

        boardBuilder.withIsQueenSideCastleAvailable(Colour.WHITE, false);
        assertFalse(boardBuilder.build().isQueenSideCastleAvailable(Colour.WHITE));

        boardBuilder.withIsQueenSideCastleAvailable(Colour.WHITE, true);
        assertTrue(boardBuilder.build().isQueenSideCastleAvailable(Colour.WHITE));

        boardBuilder.withIsQueenSideCastleAvailable(Colour.BLACK, false);
        assertFalse(boardBuilder.build().isQueenSideCastleAvailable(Colour.BLACK));

        boardBuilder.withIsQueenSideCastleAvailable(Colour.BLACK, true);
        assertTrue(boardBuilder.build().isQueenSideCastleAvailable(Colour.BLACK));
    }

    @Test
    public void testEquality() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());
        boardBuilder.withIsQueenSideCastleAvailable(Colour.BLACK, false);

        Board.BoardBuilder boardCopyBuilder = new Board.BoardBuilder(boardBuilder.build());

        assertTrue(boardBuilder.build().equals(boardCopyBuilder.build()));
        assertTrue(boardCopyBuilder.build().equals(boardBuilder.build()));

        boardCopyBuilder.withHalfMoveCount(3);
        assertFalse(boardBuilder.build().equals(boardCopyBuilder.build()));
        assertFalse(boardCopyBuilder.build().equals(boardBuilder.build()));
    }

}
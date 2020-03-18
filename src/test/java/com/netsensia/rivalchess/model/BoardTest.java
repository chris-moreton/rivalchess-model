package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.util.BoardUtils;
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
        Board board = new Board();
        board.setSquareOccupant(Square.fromCoords(1,7), SquareOccupant.BB);
        assertEquals(SquareOccupant.BB, board.getSquareOccupant(Square.fromCoords(1, 7)));
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
        final Board board = CommonTestUtils.getStartBoard();

        assertTrue(board.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(board.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(board.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(board.isQueenSideCastleAvailable(Colour.BLACK));

        board.setKingSideCastleAvailable(Colour.WHITE, false);
        assertFalse(board.isKingSideCastleAvailable(Colour.WHITE));

        board.setKingSideCastleAvailable(Colour.WHITE, true);
        assertTrue(board.isKingSideCastleAvailable(Colour.WHITE));

        board.setKingSideCastleAvailable(Colour.BLACK, false);
        assertFalse(board.isKingSideCastleAvailable(Colour.BLACK));

        board.setKingSideCastleAvailable(Colour.BLACK, true);
        assertTrue(board.isKingSideCastleAvailable(Colour.BLACK));

        board.setQueenSideCastleAvailable(Colour.WHITE, false);
        assertFalse(board.isQueenSideCastleAvailable(Colour.WHITE));

        board.setQueenSideCastleAvailable(Colour.WHITE, true);
        assertTrue(board.isQueenSideCastleAvailable(Colour.WHITE));

        board.setQueenSideCastleAvailable(Colour.BLACK, false);
        assertFalse(board.isQueenSideCastleAvailable(Colour.BLACK));

        board.setQueenSideCastleAvailable(Colour.BLACK, true);
        assertTrue(board.isQueenSideCastleAvailable(Colour.BLACK));
    }

    @Test
    public void testEquality() {
        Board board = CommonTestUtils.getStartBoard();
        board.setQueenSideCastleAvailable(Colour.BLACK, false);

        Board boardCopy = new Board(board);
        assertTrue(board.equals(boardCopy));
        assertTrue(boardCopy.equals(board));

        boardCopy.setHalfMoveCount(3);
        assertFalse(board.equals(boardCopy));
        assertFalse(boardCopy.equals(board));
    }

}
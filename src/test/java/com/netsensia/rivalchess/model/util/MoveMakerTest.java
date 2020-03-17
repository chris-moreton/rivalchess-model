package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.CommonTestUtils;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.SquareOccupant;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoveMakerTest {

    @Test
    public void testWhitePromotionMove() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.WHITE);

        board.setSquareOccupant(1, 1, SquareOccupant.WP);

        final Board newBoard = MoveMaker.makeMove(
                board, new Move(1,1,0,0, SquareOccupant.WB));

        assertEquals(SquareOccupant.WB, newBoard.getSquareOccupant(0,0));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(1,1));
    }

    @Test
    public void testBlackPromotionMove() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.BLACK);

        board.setSquareOccupant(1, 6, SquareOccupant.BP);
        board.setSquareOccupant(1, 7, SquareOccupant.NONE);

        final Board newBoard = MoveMaker.makeMove(
                board, new Move(1,6,1,7, SquareOccupant.BQ));

        assertEquals(SquareOccupant.BQ, newBoard.getSquareOccupant(1,7));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(1,6));
    }

    @Test
    public void testWhiteKingSideCastle() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.WHITE);

        board.setSquareOccupant(5, 7, SquareOccupant.NONE);
        board.setSquareOccupant(6, 7, SquareOccupant.NONE);

        final Board newBoard = MoveMaker.makeMove(
                board, new Move(4,7,6,7));

        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(4,7));
        assertEquals(SquareOccupant.WR, newBoard.getSquareOccupant(5,7));
        assertEquals(SquareOccupant.WK, newBoard.getSquareOccupant(6,7));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));

    }

    @Test
    public void testWhiteQueenSideCastle() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.WHITE);

        board.setSquareOccupant(3, 7, SquareOccupant.NONE);
        board.setSquareOccupant(2, 7, SquareOccupant.NONE);
        board.setSquareOccupant(1, 7, SquareOccupant.NONE);

        final Board newBoard = MoveMaker.makeMove(
                board, new Move(4,7,2,7));

        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(0,7));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(1,7));
        assertEquals(SquareOccupant.WK, newBoard.getSquareOccupant(2,7));
        assertEquals(SquareOccupant.WR, newBoard.getSquareOccupant(3,7));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(4,7));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
    }

    @Test
    public void testBlackKingSideCastle() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.BLACK);

        board.setSquareOccupant(5, 0, SquareOccupant.NONE);
        board.setSquareOccupant(6, 0, SquareOccupant.NONE);

        final Board newBoard = MoveMaker.makeMove(
                board, new Move(4,0,6,0));

        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(4,0));
        assertEquals(SquareOccupant.BR, newBoard.getSquareOccupant(5,0));
        assertEquals(SquareOccupant.BK, newBoard.getSquareOccupant(6,0));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));

    }

    @Test
    public void testBlackQueenSideCastle() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.BLACK);

        board.setSquareOccupant(3, 0, SquareOccupant.NONE);
        board.setSquareOccupant(2, 0, SquareOccupant.NONE);
        board.setSquareOccupant(1, 0, SquareOccupant.NONE);

        final Board newBoard = MoveMaker.makeMove(
                board, new Move(4,0,2,0));

        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(0,0));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(1,0));
        assertEquals(SquareOccupant.BK, newBoard.getSquareOccupant(2,0));
        assertEquals(SquareOccupant.BR, newBoard.getSquareOccupant(3,0));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(4,0));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
    }

    @Test
    public void testWhiteEnPassantMove() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.WHITE);

        board.setSquareOccupant(4, 3, SquareOccupant.WP);
        board.setSquareOccupant(3, 3, SquareOccupant.BP);

        final Board newBoard = MoveMaker.makeMove(
                board, new Move(4,3,3,2));

        assertEquals(SquareOccupant.WP, newBoard.getSquareOccupant(3,2));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(4,3));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(3,3));
    }

    @Test
    public void testBlackEnPassantMove() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.BLACK);

        board.setSquareOccupant(4, 4, SquareOccupant.WP);
        board.setSquareOccupant(3, 4, SquareOccupant.BP);

        final Board newBoard = MoveMaker.makeMove(
                board, new Move(3,4,4,5));

        assertEquals(SquareOccupant.BP, newBoard.getSquareOccupant(4,5));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(3,4));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(4,4));
    }

    @Test
    public void testStandardMove() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.WHITE);

        board.setSquareOccupant(4, 4, SquareOccupant.WB);

        final Board newBoard = MoveMaker.makeMove(
                board, new Move(4,4,6,6));

        assertEquals(SquareOccupant.WB, newBoard.getSquareOccupant(6,6));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(4,4));
        assertEquals(Colour.BLACK, newBoard.getSideToMove());
    }

    @Test
    public void testSetEnPassantFlag() {
        final Board board = CommonTestUtils.getStartBoard();

        assertEquals(-1, MoveMaker.makeMove(
                board, new Move(4,6,4,5)).getEnPassantFile());

        assertEquals(4, MoveMaker.makeMove(
                board, new Move(4,6,4,4)).getEnPassantFile());

        board.setSideToMove(Colour.BLACK);

        assertEquals(-1, MoveMaker.makeMove(
                board, new Move(2,1,2,2)).getEnPassantFile());

        assertEquals(2, MoveMaker.makeMove(
                board, new Move(2,1,2,3)).getEnPassantFile());

    }

    @Test
    public void testCastlingThroughCheck() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSquareOccupant(6,6, SquareOccupant.NONE);
        board.setSquareOccupant(6,7, SquareOccupant.NONE);
        board.setSquareOccupant(5,7, SquareOccupant.NONE);

        assertEquals(20, BoardUtils.getLegalMoves(board).size());
        board.setSquareOccupant(7,5, SquareOccupant.BB);
        // two kings moves and two pawn moves restricted
        assertEquals(16, BoardUtils.getLegalMoves(board).size());
    }

    @Test
    public void testDisableCastleFlagsForWhiteCastling() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSquareOccupant(6,7, SquareOccupant.NONE);
        board.setSquareOccupant(5,7, SquareOccupant.NONE);
        board.setSquareOccupant(1,7, SquareOccupant.NONE);
        board.setSquareOccupant(2,7, SquareOccupant.NONE);
        board.setSquareOccupant(3,7, SquareOccupant.NONE);

        assertTrue(board.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(board.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(board.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(board.isQueenSideCastleAvailable(Colour.BLACK));

        Board newBoard = MoveMaker.makeMove(board, new Move(4,7,6,7));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));

        newBoard = MoveMaker.makeMove(board, new Move(7,7,6,7));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));

        newBoard = MoveMaker.makeMove(board, new Move(4,7,2,7));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));

        newBoard = MoveMaker.makeMove(board, new Move(0,7,1,7));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
    }

    @Test
    public void testDisableCastleFlagsForWHITECastling() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSideToMove(Colour.BLACK);

        board.setSquareOccupant(6,0, SquareOccupant.NONE);
        board.setSquareOccupant(5,0, SquareOccupant.NONE);
        board.setSquareOccupant(1,0, SquareOccupant.NONE);
        board.setSquareOccupant(2,0, SquareOccupant.NONE);
        board.setSquareOccupant(3,0, SquareOccupant.NONE);

        assertTrue(board.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(board.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(board.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(board.isQueenSideCastleAvailable(Colour.WHITE));

        Board newBoard = MoveMaker.makeMove(board, new Move(4,0,6,0));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));

        newBoard = MoveMaker.makeMove(board, new Move(7,0,6,0));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));

        newBoard = MoveMaker.makeMove(board, new Move(4,0,2,0));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));

        newBoard = MoveMaker.makeMove(board, new Move(0,0,1,0));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
    }

    @Test
    public void testDisableCastleFlagsWhenBlackQueenRookCaptured() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSquareOccupant(0,1, SquareOccupant.WR);
        Board newBoard = MoveMaker.makeMove(board, new Move(0,1,0,0));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
    }

    @Test
    public void testDisableCastleFlagsWhenBlackKingRookCaptured() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSquareOccupant(7,1, SquareOccupant.WR);
        Board newBoard = MoveMaker.makeMove(board, new Move(7,1,7,0));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
    }

    @Test
    public void testDisableCastleFlagsWhenWhiteQueenRookCaptured() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSideToMove(Colour.BLACK);

        board.setSquareOccupant(0,6, SquareOccupant.WR);
        Board newBoard = MoveMaker.makeMove(board, new Move(0,6,0,7));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
    }

    @Test
    public void testDisableCastleFlagsWhenWhiteKingRookCaptured() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSideToMove(Colour.BLACK);

        board.setSquareOccupant(7,6, SquareOccupant.WR);
        Board newBoard = MoveMaker.makeMove(board, new Move(7,6,7,7));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
    }
}
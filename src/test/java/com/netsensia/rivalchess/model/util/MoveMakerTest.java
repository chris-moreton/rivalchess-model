package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoveMakerTest {

    @Test
    public void testWhitePromotionMove() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.WHITE);

        boardBuilder.withSquareOccupant(Square.fromCoords(1, 1), SquareOccupant.WP);

        final Board newBoard = MoveMaker.makeMove(
                boardBuilder.build(), new Move(1,1,0,0, SquareOccupant.WB));

        assertEquals(SquareOccupant.WB, newBoard.getSquareOccupant(Square.fromCoords(0,0)));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(1,1)));
    }

    @Test
    public void testBlackPromotionMove() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.BLACK);

        boardBuilder.withSquareOccupant(Square.fromCoords(1, 6), SquareOccupant.BP);
        boardBuilder.withSquareOccupant(Square.fromCoords(1, 7), SquareOccupant.NONE);

        final Board newBoard = MoveMaker.makeMove(
                boardBuilder.build(), new Move(1,6,1,7, SquareOccupant.BQ));

        assertEquals(SquareOccupant.BQ, newBoard.getSquareOccupant(Square.fromCoords(1,7)));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(1,6)));
    }

    @Test
    public void testWhiteKingSideCastle() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.WHITE);

        boardBuilder.withSquareOccupant(Square.fromCoords(5, 7), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(6, 7), SquareOccupant.NONE);

        final Board newBoard = MoveMaker.makeMove(
                boardBuilder.build(), new Move(4,7,6,7));

        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(4,7)));
        assertEquals(SquareOccupant.WR, newBoard.getSquareOccupant(Square.fromCoords(5,7)));
        assertEquals(SquareOccupant.WK, newBoard.getSquareOccupant(Square.fromCoords(6,7)));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));

    }

    @Test
    public void testWhiteQueenSideCastle() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.WHITE);

        boardBuilder.withSquareOccupant(Square.fromCoords(3, 7), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(2, 7), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(1, 7), SquareOccupant.NONE);

        final Board newBoard = MoveMaker.makeMove(
                boardBuilder.build(), new Move(4,7,2,7));

        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(0,7)));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(1,7)));
        assertEquals(SquareOccupant.WK, newBoard.getSquareOccupant(Square.fromCoords(2,7)));
        assertEquals(SquareOccupant.WR, newBoard.getSquareOccupant(Square.fromCoords(3,7)));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(4,7)));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
    }

    @Test
    public void testBlackKingSideCastle() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.BLACK);

        boardBuilder.withSquareOccupant(Square.fromCoords(5, 0), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(6, 0), SquareOccupant.NONE);

        final Board newBoard = MoveMaker.makeMove(
                boardBuilder.build(), new Move(4,0,6,0));

        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(4,0)));
        assertEquals(SquareOccupant.BR, newBoard.getSquareOccupant(Square.fromCoords(5,0)));
        assertEquals(SquareOccupant.BK, newBoard.getSquareOccupant(Square.fromCoords(6,0)));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));

    }

    @Test
    public void testBlackQueenSideCastle() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.BLACK);

        boardBuilder.withSquareOccupant(Square.fromCoords(3, 0), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(2, 0), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(1, 0), SquareOccupant.NONE);

        final Board newBoard = MoveMaker.makeMove(
                boardBuilder.build(), new Move(4,0,2,0));

        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(0,0)));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(1,0)));
        assertEquals(SquareOccupant.BK, newBoard.getSquareOccupant(Square.fromCoords(2,0)));
        assertEquals(SquareOccupant.BR, newBoard.getSquareOccupant(Square.fromCoords(3,0)));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(4,0)));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
    }

    @Test
    public void testWhiteEnPassantMove() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.WHITE);

        boardBuilder.withSquareOccupant(Square.fromCoords(4, 3), SquareOccupant.WP);
        boardBuilder.withSquareOccupant(Square.fromCoords(3, 3), SquareOccupant.BP);

        final Board newBoard = MoveMaker.makeMove(
                boardBuilder.build(), new Move(4,3,3,2));

        assertEquals(SquareOccupant.WP, newBoard.getSquareOccupant(Square.fromCoords(3,2)));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(4,3)));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(3,3)));
    }

    @Test
    public void testBlackEnPassantMove() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.BLACK);

        boardBuilder.withSquareOccupant(Square.fromCoords(4, 4), SquareOccupant.WP);
        boardBuilder.withSquareOccupant(Square.fromCoords(3, 4), SquareOccupant.BP);

        final Board newBoard = MoveMaker.makeMove(
                boardBuilder.build(), new Move(3,4,4,5));

        assertEquals(SquareOccupant.BP, newBoard.getSquareOccupant(Square.fromCoords(4,5)));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(3,4)));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(4,4)));
    }

    @Test
    public void testStandardMove() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.WHITE);

        boardBuilder.withSquareOccupant(Square.fromCoords(4, 4), SquareOccupant.WB);

        final Board newBoard = MoveMaker.makeMove(
                boardBuilder.build(), new Move(4,4,6,6));

        assertEquals(SquareOccupant.WB, newBoard.getSquareOccupant(Square.fromCoords(6,6)));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(Square.fromCoords(4,4)));
        assertEquals(Colour.BLACK, newBoard.getSideToMove());
    }

    @Test
    public void testSetEnPassantFlag() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        assertEquals(-1, MoveMaker.makeMove(
                boardBuilder.build(), new Move(4,6,4,5)).getEnPassantFile());

        assertEquals(4, MoveMaker.makeMove(
                boardBuilder.build(), new Move(4,6,4,4)).getEnPassantFile());

        boardBuilder.withSideToMove(Colour.BLACK);

        assertEquals(-1, MoveMaker.makeMove(
                boardBuilder.build(), new Move(2,1,2,2)).getEnPassantFile());

        assertEquals(2, MoveMaker.makeMove(
                boardBuilder.build(), new Move(2,1,2,3)).getEnPassantFile());

    }

    @Test
    public void testCastlingThroughCheck() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());
        boardBuilder.withSquareOccupant(Square.fromCoords(6,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(6,7), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(5,7), SquareOccupant.NONE);

        assertEquals(20, BoardUtils.getLegalMoves(boardBuilder.build()).size());
        boardBuilder.withSquareOccupant(Square.fromCoords(7,5), SquareOccupant.BB);
        // two kings moves and two pawn moves restricted
        assertEquals(16, BoardUtils.getLegalMoves(boardBuilder.build()).size());
    }

    @Test
    public void testDisableCastleFlagsForWhiteCastling() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());
        boardBuilder.withSquareOccupant(Square.fromCoords(6,7), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(5,7), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(1,7), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(2,7), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(3,7), SquareOccupant.NONE);

        assertTrue(boardBuilder.build().isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(boardBuilder.build().isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(boardBuilder.build().isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(boardBuilder.build().isQueenSideCastleAvailable(Colour.BLACK));

        Board newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(4,7,6,7));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));

        newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(7,7,6,7));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));

        newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(4,7,2,7));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));

        newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(0,7,1,7));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
    }

    @Test
    public void testDisableCastleFlagsForWHITECastling() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());
        boardBuilder.withSideToMove(Colour.BLACK);

        boardBuilder.withSquareOccupant(Square.fromCoords(6,0), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(5,0), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(1,0), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(2,0), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(3,0), SquareOccupant.NONE);

        assertTrue(boardBuilder.build().isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(boardBuilder.build().isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(boardBuilder.build().isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(boardBuilder.build().isQueenSideCastleAvailable(Colour.WHITE));

        Board newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(4,0,6,0));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));

        newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(7,0,6,0));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));

        newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(4,0,2,0));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));

        newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(0,0,1,0));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
    }

    @Test
    public void testDisableCastleFlagsWhenBlackQueenRookCaptured() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSquareOccupant(Square.fromCoords(0,1), SquareOccupant.WR);
        Board newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(0,1,0,0));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
    }

    @Test
    public void testDisableCastleFlagsWhenBlackKingRookCaptured() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSquareOccupant(Square.fromCoords(7,1), SquareOccupant.WR);
        Board newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(7,1,7,0));

        assertFalse(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
    }

    @Test
    public void testDisableCastleFlagsWhenWhiteQueenRookCaptured() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());
        boardBuilder.withSideToMove(Colour.BLACK);

        boardBuilder.withSquareOccupant(Square.fromCoords(0,6), SquareOccupant.WR);
        Board newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(0,6,0,7));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
    }

    @Test
    public void testDisableCastleFlagsWhenWhiteKingRookCaptured() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());
        boardBuilder.withSideToMove(Colour.BLACK);

        boardBuilder.withSquareOccupant(Square.fromCoords(7,6), SquareOccupant.WR);
        Board newBoard = MoveMaker.makeMove(boardBuilder.build(), new Move(7,6,7,7));

        assertTrue(newBoard.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(newBoard.isKingSideCastleAvailable(Colour.WHITE));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(newBoard.isQueenSideCastleAvailable(Colour.WHITE));
    }

    @Test
    public void enPassantCaptureDisappearingKing() {
        final Board board = Board.fromFen("8/7p/p5pb/4k3/P1pPn3/8/P5PP/1rB2RK1 b - d3 0 28");
        assertEquals(SquareOccupant.BK, board.getSquareOccupant(Square.fromCoords(4,3)));

        Board newBoard = MoveMaker.makeMove(board, new Move(2,5,3,5));
        assertEquals(SquareOccupant.BK, newBoard.getSquareOccupant(Square.fromCoords(4,3)));
    }
}
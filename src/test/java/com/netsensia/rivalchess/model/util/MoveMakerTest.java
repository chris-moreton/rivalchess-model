package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.CommonTestUtils;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.SquareOccupant;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    }
}
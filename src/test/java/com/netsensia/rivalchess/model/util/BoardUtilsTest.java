package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.CommonTestUtils;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.MoveDirection;
import com.netsensia.rivalchess.model.Piece;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BoardUtilsTest {

    @Test
    public void getBishopMoves() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSideToMove(Colour.BLACK);
        board.setSquareOccupant(3, 1, SquareOccupant.NONE);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(board, Piece.BISHOP);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(new Square(2,0), new Square(3,1)),
                        new Move(new Square(2,0), new Square(4,2)),
                        new Move(new Square(2,0), new Square(5,3)),
                        new Move(new Square(2,0), new Square(6,4)),
                        new Move(new Square(2,0), new Square(7,5))
                ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void getRookMoves() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSquareOccupant(4, 4, SquareOccupant.WR);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(board, Piece.ROOK);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(new Square(4,4), new Square(4,1)),
                        new Move(new Square(4,4), new Square(4,2)),
                        new Move(new Square(4,4), new Square(4,3)),
                        new Move(new Square(4,4), new Square(4,5)),
                        new Move(new Square(4,4), new Square(0,4)),
                        new Move(new Square(4,4), new Square(1,4)),
                        new Move(new Square(4,4), new Square(2,4)),
                        new Move(new Square(4,4), new Square(3,4)),
                        new Move(new Square(4,4), new Square(5,4)),
                        new Move(new Square(4,4), new Square(6,4)),
                        new Move(new Square(4,4), new Square(7,4))
                ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

        board.setSideToMove(Colour.BLACK);
    }

    @Test
    public void getRookMovesWhenNoMovesAvailable() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSideToMove(Colour.BLACK);
        board.setSquareOccupant(4, 4, SquareOccupant.WR);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(board, Piece.ROOK);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList());

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

    }

    @Test
    public void getQueenMoves() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSquareOccupant(4, 4, SquareOccupant.WQ);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(board, Piece.QUEEN);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(new Square(4,4), new Square(4,1)),
                        new Move(new Square(4,4), new Square(4,2)),
                        new Move(new Square(4,4), new Square(4,3)),
                        new Move(new Square(4,4), new Square(4,5)),
                        new Move(new Square(4,4), new Square(0,4)),
                        new Move(new Square(4,4), new Square(1,4)),
                        new Move(new Square(4,4), new Square(2,4)),
                        new Move(new Square(4,4), new Square(3,4)),
                        new Move(new Square(4,4), new Square(5,4)),
                        new Move(new Square(4,4), new Square(6,4)),
                        new Move(new Square(4,4), new Square(7,4)),
                        new Move(new Square(4,4), new Square(5,5)),
                        new Move(new Square(4,4), new Square(3,5)),
                        new Move(new Square(4,4), new Square(5,3)),
                        new Move(new Square(4,4), new Square(6,2)),
                        new Move(new Square(4,4), new Square(7,1)),
                        new Move(new Square(4,4), new Square(3,3)),
                        new Move(new Square(4,4), new Square(2,2)),
                        new Move(new Square(4,4), new Square(1,1))
                ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

    }

    @Test
    public void getKingMoves() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSquareOccupant(4,7, SquareOccupant.NONE);
        board.setSquareOccupant(5,2, SquareOccupant.WK);
        board.setSquareOccupant(5,3, SquareOccupant.WB);
        board.setSquareOccupant(6,2, SquareOccupant.WN);

        final List<Move> actualMoves = BoardUtils.getKingMoves(board);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(new Square(5,2), new Square(5,1)),
                        new Move(new Square(5,2), new Square(6,1)),
                        new Move(new Square(5,2), new Square(6,3)),
                        new Move(new Square(5,2), new Square(4,3)),
                        new Move(new Square(5,2), new Square(4,2)),
                        new Move(new Square(5,2), new Square(4,1))
                ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

    }

    @Test
    public void testGetDirectionalPotentialSquaresFromSquare_shouldIncludeCaptureSquare() {
        final Board board = new Board();

        board.setSquareOccupant(5, 6, SquareOccupant.BB);
        board.setSideToMove(Colour.WHITE);

        final List<Square> actualSquares =
                BoardUtils.getDirectionalSquaresFromSquare(new Square(2,3), MoveDirection.SE, board);

        final List<Square> expectedSquares =
                new ArrayList<>(Arrays.asList(
                        new Square(3,4),
                        new Square(4, 5),
                        new Square(5, 6)
                ));

        Collections.sort(expectedSquares);
        Collections.sort(actualSquares);

        assertEquals(expectedSquares, actualSquares);
    }

    @Test
    public void testGetDirectionalPotentialSquaresFromSquare_shouldNotCaptureOwnPiece() {
        final Board board = new Board();

        board.setSquareOccupant(5, 6, SquareOccupant.BB);
        board.setSideToMove(Colour.BLACK);

        final List<Square> actualSquares =
                BoardUtils.getDirectionalSquaresFromSquare(new Square(2,3), MoveDirection.SE, board);

        final List<Square> expectedSquares =
                new ArrayList<>(Arrays.asList(
                        new Square(3,4),
                        new Square(4, 5)
                ));

        Collections.sort(expectedSquares);
        Collections.sort(actualSquares);

        assertEquals(expectedSquares, actualSquares);
    }
}
package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.MoveDirection;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MovesTest {

    @Test
    public void testGetDirectionalPotentialSquaresFromSquare_shouldIncludeCaptureSquare() {
        final Board board = new Board();

        board.setSquareOccupant(5, 6, SquareOccupant.BB);
        board.setSideToMove(Colour.WHITE);

        final List<Square> actualSquares =
                Moves.getDirectionalSquaresFromSquare(new Square(2,3), MoveDirection.SE, board);

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
                Moves.getDirectionalSquaresFromSquare(new Square(2,3), MoveDirection.SE, board);

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
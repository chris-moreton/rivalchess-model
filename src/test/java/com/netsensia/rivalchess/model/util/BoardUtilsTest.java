package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.CommonTestUtils;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.MoveDirection;
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
                BoardUtils.getBishopMoves(board);

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
                BoardUtils.getRookMoves(board);

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
                BoardUtils.getRookMoves(board);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList());

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

    }
}
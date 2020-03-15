package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.util.BoardUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Test
    public void testSetSquareOccupant() {
        Board board = new Board();
        board.setSquareOccupant(new Square(1,7), SquareOccupant.BB);
        assertEquals(SquareOccupant.BB, board.getSquareOccupant(1, 7));
    }

    @Test
    public void testGetPieceSquares() {

        final List<Square> expectedSquares =
                new ArrayList<>(Arrays.asList(
                        new Square(6, 0),
                        new Square(1,0)
                ));

        Collections.sort(expectedSquares);

        assertEquals(expectedSquares, BoardUtils.getSquaresWithOccupant(
                CommonTestUtils.getStartBoard(), SquareOccupant.BN));
    }

}
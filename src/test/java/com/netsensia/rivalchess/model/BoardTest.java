package com.netsensia.rivalchess.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Test
    public void testCustomRankAndFileCounts() {
        Board board = new Board(10, 12);

        board.setSquareOccupant(9, 11, SquareOccupant.BQ);
        assertEquals(SquareOccupant.BQ, board.getSquareOccupant(9, 11));

        Square square = new Square(9, 11);
        assertEquals(SquareOccupant.BQ, board.getSquareOccupant(square));
    }
}
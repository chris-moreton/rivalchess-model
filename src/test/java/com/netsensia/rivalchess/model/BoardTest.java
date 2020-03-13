package com.netsensia.rivalchess.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Test
    public void testSetSquareOccupant() {
        Board board = new Board();
        board.setSquareOccupant(new Square(1,7), SquareOccupant.BB);
        assertEquals(SquareOccupant.BB, board.getSquareOccupant(1, 7));
    }

}
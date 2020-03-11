package com.netsensia.rivalchess.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Test
    public void testCustomRankAndFileCounts() {
        Board board = new Board(10, 12);

        board.setPieceCode(9, 11, 'q');
        assertEquals('q', board.getPieceCode(9, 11));

        Square square = new Square(9, 11);
        assertEquals('q', board.getPieceCode(square));
    }
}
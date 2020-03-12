package com.netsensia.rivalchess.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MoveTest {

    @Test
    public void testConstructFromFileAndRankRefs() {

        final Move move1 = new Move(3, 1, 4, 2);

        assertEquals(3, move1.getSrcXFile());
        assertEquals(1, move1.getSrcYRank());
        assertEquals(4, move1.getTgtXFile());
        assertEquals(2, move1.getTgtYRank());
    }

    @Test
    public void testConstructFromSquareRefs() {
        final Square square1 = new Square(3,1);
        final Square square2 = new Square(4, 2);
        final Move move1 = new Move(square1, square2);

        assertEquals(3, move1.getSrcXFile());
        assertEquals(1, move1.getSrcYRank());
        assertEquals(4, move1.getTgtXFile());
        assertEquals(2, move1.getTgtYRank());
    }

    @Test
    public void testToString() {
        final Move move1 = new Move(3,1,4,2);
        assertEquals("d7e6", move1.toString());

        final Move move2 = new Move(0,6,1,7);
        move2.setPromotedPiece(SquareOccupant.BQ);
        assertEquals("a2b1q", move2.toString());

        final Move move3 = new Move(0,1,1,0);
        move3.setPromotedPiece(SquareOccupant.WQ);
        assertEquals("a7b8Q", move3.toString());
    }

    @Test
    public void testEquals() {
        final Move move1 = new Move(3,1,4,2);
        final Move move2 = new Move(0,6,1,7);
        final Move move3 = new Move(0,6,1,7);
        final Move move4 = new Move(3,1,4,2);
        final Move move5 = new Move(2,1,4,2);
        final Move move6 = new Move(0,6,1,7);

        assertEquals(move1, move4);
        assertEquals(move2, move3);
        assertEquals(move2, move6);
        assertEquals(move3, move6);

        assertNotEquals(move1, move2);
        assertNotEquals(move1, move3);
        assertNotEquals(move1, move5);
        assertNotEquals(move1, move6);
    }

    @Test
    public void testGetPromotedPiece() {
        final Move move1 = new Move(3,1,4,2);
    }
}
package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.MoveDirection;
import com.netsensia.rivalchess.model.Square;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MovesTest {

    @Test
    public void testIsNumberInRange() {
        assertFalse(Moves.isValidRankFileBoardReference(-2));
        assertFalse(Moves.isValidRankFileBoardReference(-1));
        assertTrue(Moves.isValidRankFileBoardReference(0));
        assertTrue(Moves.isValidRankFileBoardReference(1));
        assertTrue(Moves.isValidRankFileBoardReference(2));
        assertTrue(Moves.isValidRankFileBoardReference(3));
        assertTrue(Moves.isValidRankFileBoardReference(4));
        assertTrue(Moves.isValidRankFileBoardReference(5));
        assertTrue(Moves.isValidRankFileBoardReference(6));
        assertTrue(Moves.isValidRankFileBoardReference(7));
        assertFalse(Moves.isValidRankFileBoardReference(8));
        assertFalse(Moves.isValidRankFileBoardReference(9));
    }

    @Test
    public void testGetDirectionalPotentialSquaresFromSquare() {
        final List<Square> squares = Moves.getDirectionalPotentialSquaresFromSquare(new Square(2,3), MoveDirection.SE);
    }
}
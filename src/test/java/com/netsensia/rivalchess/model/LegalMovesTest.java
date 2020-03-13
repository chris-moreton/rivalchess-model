package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.util.Moves;
import org.junit.Test;

import static org.junit.Assert.*;

public class LegalMovesTest {

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
}
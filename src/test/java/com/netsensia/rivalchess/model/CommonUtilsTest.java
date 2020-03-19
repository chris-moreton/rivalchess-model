package com.netsensia.rivalchess.model;

import org.junit.Test;

import static com.netsensia.rivalchess.model.util.CommonUtils.isValidRankFileBoardReference;
import static org.junit.Assert.*;

public class CommonUtilsTest {

    @Test
    public void testIsNumberInRange() {
        assertFalse(isValidRankFileBoardReference(-2));
        assertFalse(isValidRankFileBoardReference(-1));
        assertTrue(isValidRankFileBoardReference(0));
        assertTrue(isValidRankFileBoardReference(1));
        assertTrue(isValidRankFileBoardReference(2));
        assertTrue(isValidRankFileBoardReference(3));
        assertTrue(isValidRankFileBoardReference(4));
        assertTrue(isValidRankFileBoardReference(5));
        assertTrue(isValidRankFileBoardReference(6));
        assertTrue(isValidRankFileBoardReference(7));
        assertFalse(isValidRankFileBoardReference(8));
        assertFalse(isValidRankFileBoardReference(9));
    }
}
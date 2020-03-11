package com.netsensia.rivalchess.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColourTest {

    @Test
    public void opponentTest() {
        assertEquals(Colour.WHITE, Colour.BLACK.opponent());
        assertEquals(Colour.BLACK, Colour.WHITE.opponent());
    }
}
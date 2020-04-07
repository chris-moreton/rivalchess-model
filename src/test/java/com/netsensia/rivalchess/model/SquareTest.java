package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.helper.SliderDirection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class SquareTest {

    @Test
    public void testConstructorAndGettersAndSetters() {
        Square br = Square.fromCoords(1,2);

        assertEquals(1, br.getXFile());
        assertEquals(2, br.getYRank());
    }

    @Test
    public void testEquals() {
        Square br1 = Square.fromCoords(1, 2);
        Square br2 = Square.fromCoords(2, 1);
        Square br3 = Square.fromCoords(1, 2);

        Object o = new Object();

        assertEquals(br1, br3);
        assertNotEquals(br1, br2);
        assertEquals(br3, br1);
        assertNotEquals(br2, br1);
        assertNotEquals(br2, br3);
        assertNotEquals(br3, br2);

        assertNotEquals(o, br1);
        assertNotEquals(br1, o);

    }

    @Test
    public void testGetAlgebraicXFile() {
        Square br1 = Square.fromCoords(1, 2);
        assertEquals('b', br1.getAlgebraicXFile());
    }

    @Test
    public void testGetAlgebraicYRank() {
        Square br1 = Square.fromCoords(1, 2);
        assertEquals('6', br1.getAlgebraicYRank());
    }

    @Test
    public void createFromAlgebraic() {
        Square square = Square.fromAlgebraic("b6");
        assertEquals(1, square.getXFile());
        assertEquals(2, square.getYRank());
        assertEquals('b', square.getAlgebraicXFile());
        assertEquals('6', square.getAlgebraicYRank());
    }

    @Test
    public void testFromCoords() {
        assertEquals(Square.A8, Square.fromCoords(0,0));
        assertEquals(Square.B8, Square.fromCoords(1,0));
        assertEquals(Square.C8, Square.fromCoords(2,0));
        assertEquals(Square.D8, Square.fromCoords(3,0));
        assertEquals(Square.E8, Square.fromCoords(4,0));
        assertEquals(Square.F8, Square.fromCoords(5,0));
        assertEquals(Square.G8, Square.fromCoords(6,0));
        assertEquals(Square.H8, Square.fromCoords(7,0));
        assertEquals(Square.A7, Square.fromCoords(0,1));
        assertEquals(Square.B7, Square.fromCoords(1,1));
        assertEquals(Square.C7, Square.fromCoords(2,1));
        assertEquals(Square.D7, Square.fromCoords(3,1));
        assertEquals(Square.E7, Square.fromCoords(4,1));
        assertEquals(Square.F7, Square.fromCoords(5,1));
        assertEquals(Square.G7, Square.fromCoords(6,1));
        assertEquals(Square.H7, Square.fromCoords(7,1));
        assertEquals(Square.A6, Square.fromCoords(0,2));
        assertEquals(Square.B6, Square.fromCoords(1,2));
        assertEquals(Square.C6, Square.fromCoords(2,2));
        assertEquals(Square.D6, Square.fromCoords(3,2));
        assertEquals(Square.E6, Square.fromCoords(4,2));
        assertEquals(Square.F6, Square.fromCoords(5,2));
        assertEquals(Square.G6, Square.fromCoords(6,2));
        assertEquals(Square.H6, Square.fromCoords(7,2));
        assertEquals(Square.A5, Square.fromCoords(0,3));
        assertEquals(Square.B5, Square.fromCoords(1,3));
        assertEquals(Square.C5, Square.fromCoords(2,3));
        assertEquals(Square.D5, Square.fromCoords(3,3));
        assertEquals(Square.E5, Square.fromCoords(4,3));
        assertEquals(Square.F5, Square.fromCoords(5,3));
        assertEquals(Square.G5, Square.fromCoords(6,3));
        assertEquals(Square.H5, Square.fromCoords(7,3));
        assertEquals(Square.A4, Square.fromCoords(0,4));
        assertEquals(Square.B4, Square.fromCoords(1,4));
        assertEquals(Square.C4, Square.fromCoords(2,4));
        assertEquals(Square.D4, Square.fromCoords(3,4));
        assertEquals(Square.E4, Square.fromCoords(4,4));
        assertEquals(Square.F4, Square.fromCoords(5,4));
        assertEquals(Square.G4, Square.fromCoords(6,4));
        assertEquals(Square.H4, Square.fromCoords(7,4));
        assertEquals(Square.A3, Square.fromCoords(0,5));
        assertEquals(Square.B3, Square.fromCoords(1,5));
        assertEquals(Square.C3, Square.fromCoords(2,5));
        assertEquals(Square.D3, Square.fromCoords(3,5));
        assertEquals(Square.E3, Square.fromCoords(4,5));
        assertEquals(Square.F3, Square.fromCoords(5,5));
        assertEquals(Square.G3, Square.fromCoords(6,5));
        assertEquals(Square.H3, Square.fromCoords(7,5));
        assertEquals(Square.A2, Square.fromCoords(0,6));
        assertEquals(Square.B2, Square.fromCoords(1,6));
        assertEquals(Square.C2, Square.fromCoords(2,6));
        assertEquals(Square.D2, Square.fromCoords(3,6));
        assertEquals(Square.E2, Square.fromCoords(4,6));
        assertEquals(Square.F2, Square.fromCoords(5,6));
        assertEquals(Square.G2, Square.fromCoords(6,6));
        assertEquals(Square.H2, Square.fromCoords(7,6));
        assertEquals(Square.A1, Square.fromCoords(0,7));
        assertEquals(Square.B1, Square.fromCoords(1,7));
        assertEquals(Square.C1, Square.fromCoords(2,7));
        assertEquals(Square.D1, Square.fromCoords(3,7));
        assertEquals(Square.E1, Square.fromCoords(4,7));
        assertEquals(Square.F1, Square.fromCoords(5,7));
        assertEquals(Square.G1, Square.fromCoords(6,7));
        assertEquals(Square.H1, Square.fromCoords(7,7));
    }

    @Test
    public void testIsValidDirection() {
        assertTrue(Square.A1.isValidDirection(SliderDirection.N));
        assertTrue(Square.A1.isValidDirection(SliderDirection.NE));
        assertTrue(Square.A1.isValidDirection(SliderDirection.E));
        assertFalse(Square.A1.isValidDirection(SliderDirection.SE));
        assertFalse(Square.A1.isValidDirection(SliderDirection.S));
        assertFalse(Square.A1.isValidDirection(SliderDirection.SW));
        assertFalse(Square.A1.isValidDirection(SliderDirection.W));
        assertFalse(Square.A1.isValidDirection(SliderDirection.NW));
    }
}

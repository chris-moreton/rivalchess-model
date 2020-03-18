package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.Square;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
        assertEquals('6', br1.getAlgebraicYRank(8));
    }

    @Test
    public void createFromAlgebraic() {
        Square square = Square.fromAlgebraic("b6");
        assertEquals(1, square.getXFile());
        assertEquals(2, square.getYRank());
        assertEquals('b', square.getAlgebraicXFile());
        assertEquals('6', square.getAlgebraicYRank(8));
    }
}

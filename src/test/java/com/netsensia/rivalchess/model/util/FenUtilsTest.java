package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;
import com.netsensia.rivalchess.model.exception.IllegalFenException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class FenUtilsTest {

    private static final String INVALID_FEN_MESSAGE =
            "Expected at least 2 sections to FEN - board and mover";
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void throwsExceptionWhenNotTwoPartsToFen() {
        exception.expect(IllegalFenException.class);
        exception.expectMessage(
                INVALID_FEN_MESSAGE);
        final Board board = FenUtils.getBoardModel("6k1/6p1/1p2q2p/1p5P/1P3RP1/2PK1B2/1r2N3/8");
    }

    @Test
    public void throwsExceptionWhenEmptyFen() {
        exception.expect(IllegalFenException.class);
        exception.expectMessage(
                INVALID_FEN_MESSAGE);
        final Board board = FenUtils.getBoardModel("  ");
    }

    @Test
    public void throwsExceptionWhenFenPartHasAnIllegalCharacter() {
        exception.expect(IllegalFenException.class);
        exception.expectMessage(
                "Unexpected character in FEN: j");
        final Board board = FenUtils.getBoardModel("6k1/6p2/1p2j2p/1p5P/1P3RP1/2PK1B2/1r2N3/8 b - g3 5 7");
    }

    @Test
    public void throwsExceptionWhenInvalidMover() {
        exception.expect(IllegalFenException.class);
        exception.expectMessage(
                "Invalid side to move: o");
        final Board board = FenUtils.getBoardModel("6k1/6p2/1p2q2p/1p5P/1P3RP1/2PK1B2/1r2N3/8 o - g3 5 7");
    }

    @Test
    public void throwsExceptionWhenMoverIsBlank() {
        exception.expect(IllegalFenException.class);
        exception.expectMessage(
                INVALID_FEN_MESSAGE);
        final Board board = FenUtils.getBoardModel("6k1/6p2/1p2q2p/1p5P/1P3RP1/2PK1B2/1r2N3/8   - g3 5 7");
    }

    @Test
    public void shouldSetSensibleDefaults() {
        exception.expect(IllegalFenException.class);
        exception.expectMessage(
                INVALID_FEN_MESSAGE);
        final Board board = FenUtils.getBoardModel("6k1/6p2/1p2q2p/1p5P/1P3RP1/2PK1B2/1r2N3/8 b");

        assertEquals(-1, board.getEnPassantFile());

        assertTrue(board.isKingSideCastleAvailable(Colour.BLACK));
        assertTrue(board.isKingSideCastleAvailable(Colour.WHITE));

        assertTrue(board.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(board.isQueenSideCastleAvailable(Colour.WHITE));

        assertEquals(0, board.getHalfMoveCount());
        assertEquals(0, board.getFullMoveCount());
    }

    @Test
    public void testGetChessBoard() throws IllegalFenException {

        Board board = FenUtils.getBoardModel("6k1/6p1/1p2q2p/1p5P/1P3RP1/2PK1B2/1r2N3/8 b kQ g3 5 56");

        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(0,0)));
        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(0,1)));
        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(0,2)));
        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(0,3)));
        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(0,4)));
        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(0,5)));
        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(0,6)));
        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(0,7)));

        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(1,7)));
        assertEquals(SquareOccupant.BR, board.getSquareOccupant(Square.fromCoords(1,6)));
        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(1,5)));
        assertEquals(SquareOccupant.WP, board.getSquareOccupant(Square.fromCoords(1,4)));
        assertEquals(SquareOccupant.BP, board.getSquareOccupant(Square.fromCoords(1,3)));
        assertEquals(SquareOccupant.BP, board.getSquareOccupant(Square.fromCoords(1,2)));
        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(1,1)));
        assertEquals(SquareOccupant.NONE, board.getSquareOccupant(Square.fromCoords(1,0)));

        assertEquals(Colour.BLACK, board.getSideToMove());
        assertEquals(6, board.getEnPassantFile());

        assertTrue(board.isKingSideCastleAvailable(Colour.BLACK));
        assertFalse(board.isKingSideCastleAvailable(Colour.WHITE));

        assertFalse(board.isQueenSideCastleAvailable(Colour.BLACK));
        assertTrue(board.isQueenSideCastleAvailable(Colour.WHITE));

        assertEquals(5, board.getHalfMoveCount());
        assertEquals(56, board.getFullMoveCount());
    }

    @Test
    public void testInvertFen() throws IllegalFenException {
        String actual = FenUtils.invertFen("6k1/6p1/1p2q2p/1p5P/1P3RP1/2PK1B2/1r2N3/8 b - g3 5 56");
        assertEquals("8/1R2n3/2pk1b2/1p3rp1/1P5p/1P2Q2P/6P1/6K1 w - b6 5 56", actual);
    }
}
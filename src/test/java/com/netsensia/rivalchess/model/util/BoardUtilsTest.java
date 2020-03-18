package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.CommonTestUtils;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.helper.SliderDirection;
import com.netsensia.rivalchess.model.Piece;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BoardUtilsTest {

    @Test
    public void getBishopMoves() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSideToMove(Colour.BLACK);
        board.setSquareOccupant(Square.fromCoords(3, 1), SquareOccupant.NONE);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(board, Piece.BISHOP);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(Square.fromCoords(2,0), Square.fromCoords(3,1)),
                        new Move(Square.fromCoords(2,0), Square.fromCoords(4,2)),
                        new Move(Square.fromCoords(2,0), Square.fromCoords(5,3)),
                        new Move(Square.fromCoords(2,0), Square.fromCoords(6,4)),
                        new Move(Square.fromCoords(2,0), Square.fromCoords(7,5))
                ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void getRookMoves() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSquareOccupant(Square.fromCoords(4, 4), SquareOccupant.WR);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(board, Piece.ROOK);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(Square.fromCoords(4,4), Square.fromCoords(4,1)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(4,2)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(4,3)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(4,5)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(0,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(1,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(2,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(3,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(5,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(6,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(7,4))
                ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

        board.setSideToMove(Colour.BLACK);
    }

    @Test
    public void getRookMovesWhenNoMovesAvailable() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSideToMove(Colour.BLACK);
        board.setSquareOccupant(Square.fromCoords(4, 4), SquareOccupant.WR);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(board, Piece.ROOK);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList());

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

    }

    @Test
    public void getQueenMoves() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setSquareOccupant(Square.fromCoords(4, 4), SquareOccupant.WQ);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(board, Piece.QUEEN);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(Square.fromCoords(4,4), Square.fromCoords(4,1)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(4,2)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(4,3)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(4,5)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(0,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(1,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(2,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(3,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(5,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(6,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(7,4)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(5,5)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(3,5)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(5,3)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(6,2)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(7,1)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(3,3)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(2,2)),
                        new Move(Square.fromCoords(4,4), Square.fromCoords(1,1))
                ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

    }

    @Test
    public void getKingMoves() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSquareOccupant(Square.fromCoords(4,7), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(5,2), SquareOccupant.WK);
        board.setSquareOccupant(Square.fromCoords(5,3), SquareOccupant.WB);
        board.setSquareOccupant(Square.fromCoords(6,2), SquareOccupant.WN);

        final List<Move> actualMoves = BoardUtils.getKingMoves(board);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(Square.fromCoords(5,2), Square.fromCoords(5,1)),
                        new Move(Square.fromCoords(5,2), Square.fromCoords(6,1)),
                        new Move(Square.fromCoords(5,2), Square.fromCoords(6,3)),
                        new Move(Square.fromCoords(5,2), Square.fromCoords(4,3)),
                        new Move(Square.fromCoords(5,2), Square.fromCoords(4,2)),
                        new Move(Square.fromCoords(5,2), Square.fromCoords(4,1))
                ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

    }

    @Test
    public void testNoCastlingAvailable() {
        final Board board = CommonTestUtils.getStartBoard();

        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));
    }

    @Test
    public void testCastlingAvailableButBlocked() {
        final Board board = CommonTestUtils.getStartBoard();
        board.setKingSideCastleAvailable(Colour.WHITE, true);
        board.setKingSideCastleAvailable(Colour.BLACK, true);
        board.setQueenSideCastleAvailable(Colour.WHITE, true);
        board.setQueenSideCastleAvailable(Colour.BLACK, true);

        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));
    }

    @Test
    public void testWhiteKingSideCastling() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setKingSideCastleAvailable(Colour.WHITE, true);
        board.setKingSideCastleAvailable(Colour.BLACK, true);
        board.setQueenSideCastleAvailable(Colour.WHITE, true);
        board.setQueenSideCastleAvailable(Colour.BLACK, true);

        board.setSquareOccupant(Square.fromCoords(5, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(6, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList(
                new Move(Square.fromCoords(4,7), Square.fromCoords(6,7))
        )), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(7, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(7, 7), SquareOccupant.WR);
        board.setSquareOccupant(Square.fromCoords(4, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));
    }

    @Test
    public void testWhiteQueenSideCastling() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setKingSideCastleAvailable(Colour.WHITE, true);
        board.setKingSideCastleAvailable(Colour.BLACK, true);
        board.setQueenSideCastleAvailable(Colour.WHITE, true);
        board.setQueenSideCastleAvailable(Colour.BLACK, true);

        board.setSquareOccupant(Square.fromCoords(3, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(2, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(1, 7), SquareOccupant.NONE);

        assertEquals(new ArrayList<>(Arrays.asList(
                new Move(Square.fromCoords(4,7), Square.fromCoords(2,7))
        )), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(0, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(0, 7), SquareOccupant.WR);
        board.setSquareOccupant(Square.fromCoords(4, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));
    }

    @Test
    public void testBlackKingSideCastling() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.BLACK);

        board.setKingSideCastleAvailable(Colour.WHITE, true);
        board.setKingSideCastleAvailable(Colour.BLACK, true);
        board.setQueenSideCastleAvailable(Colour.WHITE, true);
        board.setQueenSideCastleAvailable(Colour.BLACK, true);

        board.setSquareOccupant(Square.fromCoords(5, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(6, 0), SquareOccupant.NONE);

        assertEquals(new ArrayList<>(Arrays.asList(
                new Move(Square.fromCoords(4,0), Square.fromCoords(6,0))
        )), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(7, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(7, 0), SquareOccupant.WR);
        board.setSquareOccupant(Square.fromCoords(4, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));
    }

    @Test
    public void testBlackQueenSideCastling() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.BLACK);

        board.setKingSideCastleAvailable(Colour.WHITE, true);
        board.setKingSideCastleAvailable(Colour.BLACK, true);
        board.setQueenSideCastleAvailable(Colour.WHITE, true);
        board.setQueenSideCastleAvailable(Colour.BLACK, true);

        board.setSquareOccupant(Square.fromCoords(3, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(2, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(1, 0), SquareOccupant.NONE);

        assertEquals(new ArrayList<>(Arrays.asList(
                new Move(Square.fromCoords(4,0), Square.fromCoords(2,0))
        )), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(0, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));

        board.setSquareOccupant(Square.fromCoords(0, 0), SquareOccupant.WR);
        board.setSquareOccupant(Square.fromCoords(4, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(board));
    }

    @Test
    public void getKingMovesWhenKingOnAnEdge() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSquareOccupant(Square.fromCoords(4,7), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(7,2), SquareOccupant.WK);

        final List<Move> actualMoves = BoardUtils.getKingMoves(board);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(Square.fromCoords(7,2), Square.fromCoords(7,1)),
                        new Move(Square.fromCoords(7,2), Square.fromCoords(7,3)),
                        new Move(Square.fromCoords(7,2), Square.fromCoords(6,3)),
                        new Move(Square.fromCoords(7,2), Square.fromCoords(6,2)),
                        new Move(Square.fromCoords(7,2), Square.fromCoords(6,1))
                ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

    }

    @Test
    public void getKnightMoves() {
        final Board board = CommonTestUtils.getStartBoard();

        final List<Move> actualMoves = BoardUtils.getKnightMoves(board);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(Square.fromCoords(1,7), Square.fromCoords(2,5)),
                        new Move(Square.fromCoords(1,7), Square.fromCoords(0,5)),
                        new Move(Square.fromCoords(6,7), Square.fromCoords(7,5)),
                        new Move(Square.fromCoords(6,7), Square.fromCoords(5,5))
                ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

    }

    @Test
    public void testGetDirectionalPotentialSquaresFromSquare_shouldIncludeCaptureSquare() {
        final Board board = new Board();

        board.setSquareOccupant(Square.fromCoords(5, 6), SquareOccupant.BB);
        board.setSideToMove(Colour.WHITE);

        final List<Square> actualSquares =
                BoardUtils.getDirectionalSquaresFromSquare(Square.fromCoords(2,3), SliderDirection.SE, board);

        final List<Square> expectedSquares =
                new ArrayList<>(Arrays.asList(
                        Square.fromCoords(3,4),
                        Square.fromCoords(4, 5),
                        Square.fromCoords(5, 6)
                ));

        Collections.sort(expectedSquares);
        Collections.sort(actualSquares);

        assertEquals(expectedSquares, actualSquares);
    }

    @Test
    public void testGetDirectionalPotentialSquaresFromSquare_shouldNotCaptureOwnPiece() {
        final Board board = new Board();

        board.setSquareOccupant(Square.fromCoords(5, 6), SquareOccupant.BB);
        board.setSideToMove(Colour.BLACK);

        final List<Square> actualSquares =
                BoardUtils.getDirectionalSquaresFromSquare(Square.fromCoords(2,3), SliderDirection.SE, board);

        final List<Square> expectedSquares =
                new ArrayList<>(Arrays.asList(
                        Square.fromCoords(3,4),
                        Square.fromCoords(4, 5)
                ));

        Collections.sort(expectedSquares);
        Collections.sort(actualSquares);

        assertEquals(expectedSquares, actualSquares);
    }

    @Test
    public void getWhitePawnMoves() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSquareOccupant(Square.fromCoords(1,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(1,3), SquareOccupant.WP);

        board.setSquareOccupant(Square.fromCoords(2,1), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(2,3), SquareOccupant.BP);

        board.setSquareOccupant(Square.fromCoords(6,1), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(6,2), SquareOccupant.BP);

        board.setSquareOccupant(Square.fromCoords(6,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(6,4), SquareOccupant.WP);

        board.setSquareOccupant(Square.fromCoords(5,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(5,5), SquareOccupant.WP);

        board.setSquareOccupant(Square.fromCoords(5,0), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(4,4), SquareOccupant.BB);

        board.setSquareOccupant(Square.fromCoords(3,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(4,2), SquareOccupant.WP);

        board.setSquareOccupant(Square.fromCoords(1,0), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(2,5), SquareOccupant.BN);

        board.setSquareOccupant(Square.fromCoords(7,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(7,1), SquareOccupant.WP);

        board.setEnPassantFile(2);

        final List<Move> actualMoves = BoardUtils.getPawnMoves(board);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(Square.fromCoords(0,6), Square.fromCoords(0,5)),
                        new Move(Square.fromCoords(0,6), Square.fromCoords(0,4)),
                        new Move(Square.fromCoords(1,3), Square.fromCoords(1,2)),
                        new Move(Square.fromCoords(1,3), Square.fromCoords(2,2)),
                        new Move(Square.fromCoords(4,2), Square.fromCoords(3,1)),
                        new Move(Square.fromCoords(4,2), Square.fromCoords(5,1)),
                        new Move(Square.fromCoords(4,6), Square.fromCoords(4,5)),
                        new Move(Square.fromCoords(5,5), Square.fromCoords(4,4)),
                        new Move(Square.fromCoords(5,5), Square.fromCoords(5,4)),
                        new Move(Square.fromCoords(6,4), Square.fromCoords(6,3)),
                        new Move(Square.fromCoords(7,1), Square.fromCoords(6,0), SquareOccupant.WB),
                        new Move(Square.fromCoords(7,1), Square.fromCoords(6,0), SquareOccupant.WN),
                        new Move(Square.fromCoords(7,1), Square.fromCoords(6,0), SquareOccupant.WR),
                        new Move(Square.fromCoords(7,1), Square.fromCoords(6,0), SquareOccupant.WQ)
                        ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

    }

    @Test
    public void getWhitePawnMovesWhenJumpLandingSquareIsBlocked() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSquareOccupant(Square.fromCoords(4,4), SquareOccupant.BP);
        board.setSquareOccupant(Square.fromCoords(3,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(2,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(1,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(0,6), SquareOccupant.NONE);

        final List<Move> actualMoves = BoardUtils.getPawnMoves(board);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(Square.fromCoords(4,6), Square.fromCoords(4,5)),
                        new Move(Square.fromCoords(5,6), Square.fromCoords(5,5)),
                        new Move(Square.fromCoords(5,6), Square.fromCoords(5,4)),
                        new Move(Square.fromCoords(6,6), Square.fromCoords(6,5)),
                        new Move(Square.fromCoords(6,6), Square.fromCoords(6,4)),
                        new Move(Square.fromCoords(7,6), Square.fromCoords(7,5)),
                        new Move(Square.fromCoords(7,6), Square.fromCoords(7,4))
                        ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void getBlackPawnMoves() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.BLACK);

        board.setSquareOccupant(Square.fromCoords(1,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(1,3), SquareOccupant.WP);

        board.setSquareOccupant(Square.fromCoords(2,1), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(2,3), SquareOccupant.BP);

        board.setSquareOccupant(Square.fromCoords(6,1), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(6,2), SquareOccupant.BP);

        board.setSquareOccupant(Square.fromCoords(6,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(6,4), SquareOccupant.WP);

        board.setSquareOccupant(Square.fromCoords(5,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(5,5), SquareOccupant.WP);

        board.setSquareOccupant(Square.fromCoords(5,0), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(4,4), SquareOccupant.BB);

        board.setSquareOccupant(Square.fromCoords(3,6), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(4,2), SquareOccupant.WP);

        board.setSquareOccupant(Square.fromCoords(1,0), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(2,5), SquareOccupant.BN);

        board.setSquareOccupant(Square.fromCoords(7,1), SquareOccupant.NONE);
        board.setSquareOccupant(Square.fromCoords(7,6), SquareOccupant.BP);

        board.setSquareOccupant(Square.fromCoords(7,7), SquareOccupant.NONE);

        board.setEnPassantFile(6);

        final List<Move> actualMoves = BoardUtils.getPawnMoves(board);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList(
                        new Move(Square.fromCoords(0,1), Square.fromCoords(0,2)),
                        new Move(Square.fromCoords(0,1), Square.fromCoords(0,3)),
                        new Move(Square.fromCoords(1,1), Square.fromCoords(1,2)),
                        new Move(Square.fromCoords(2,3), Square.fromCoords(2,4)),
                        new Move(Square.fromCoords(3,1), Square.fromCoords(3,2)),
                        new Move(Square.fromCoords(3,1), Square.fromCoords(3,3)),
                        new Move(Square.fromCoords(3,1), Square.fromCoords(4,2)),
                        new Move(Square.fromCoords(5,1), Square.fromCoords(4,2)),
                        new Move(Square.fromCoords(5,1), Square.fromCoords(5,2)),
                        new Move(Square.fromCoords(5,1), Square.fromCoords(5,3)),
                        new Move(Square.fromCoords(6,2), Square.fromCoords(6,3)),
                        new Move(Square.fromCoords(7,6), Square.fromCoords(6,7), SquareOccupant.BB),
                        new Move(Square.fromCoords(7,6), Square.fromCoords(6,7), SquareOccupant.BN),
                        new Move(Square.fromCoords(7,6), Square.fromCoords(6,7), SquareOccupant.BR),
                        new Move(Square.fromCoords(7,6), Square.fromCoords(6,7), SquareOccupant.BQ),
                        new Move(Square.fromCoords(7,6), Square.fromCoords(7,7), SquareOccupant.BB),
                        new Move(Square.fromCoords(7,6), Square.fromCoords(7,7), SquareOccupant.BN),
                        new Move(Square.fromCoords(7,6), Square.fromCoords(7,7), SquareOccupant.BR),
                        new Move(Square.fromCoords(7,6), Square.fromCoords(7,7), SquareOccupant.BQ)
                        ));

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void testIsCheck() {
        final Board board = CommonTestUtils.getStartBoard();
        assertFalse(BoardUtils.isCheck(board));

        board.setSquareOccupant(Square.fromCoords(4,6), SquareOccupant.BR);
        assertTrue(BoardUtils.isCheck(board));

        board.setSquareOccupant(Square.fromCoords(4,6), SquareOccupant.WR);
        assertFalse(BoardUtils.isCheck(board));

        board.setSideToMove(Colour.BLACK);
        assertFalse(BoardUtils.isCheck(board));

    }

    @Test
    public void testRemoveChecksFromMoves() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSquareOccupant(Square.fromCoords(7,4), SquareOccupant.BB);
        assertFalse(BoardUtils.isCheck(board));

        assertEquals(19, BoardUtils.getAllMovesWithoutRemovingChecks(board).size());
        assertEquals(17, BoardUtils.getLegalMoves(board).size());
    }

    @Test
    public void testIsSquareAttacked() {
        final Board board = Board.fromFen("8/7p/p5pb/4k3/P1pPn3/8/P5PP/1rB2RK1 b - d3 0 28");

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,0), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,0), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,1), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,1), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,2), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,2), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,3), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,3), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,4), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,4), Colour.BLACK));

        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,5), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,5), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,6), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,6), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,7), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(0,7), Colour.BLACK));

        /*****************************************************************************************/

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,0), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,0), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,1), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,1), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,2), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,2), Colour.BLACK));

        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,3), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,3), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,4), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,4), Colour.BLACK));

        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,5), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,5), Colour.BLACK));

        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,6), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,6), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,7), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(1,7), Colour.BLACK));

        /*****************************************************************************************/

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,0), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,0), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,1), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,1), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,2), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,2), Colour.BLACK));

        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,3), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,3), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,4), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,4), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,5), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,5), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,6), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,6), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,7), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttacked(board, Square.fromCoords(2,7), Colour.BLACK));
    }
}
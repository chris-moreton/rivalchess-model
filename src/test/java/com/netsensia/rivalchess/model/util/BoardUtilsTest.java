package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Colour;
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
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.BLACK);
        boardBuilder.withSquareOccupant(Square.fromCoords(3, 1), SquareOccupant.NONE);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(boardBuilder.build(), Piece.BISHOP);

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
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSquareOccupant(Square.fromCoords(4, 4), SquareOccupant.WR);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(boardBuilder.build(), Piece.ROOK);

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
    }

    @Test
    public void getRookMovesWhenNoMovesAvailable() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());
        boardBuilder.withSideToMove(Colour.BLACK);
        boardBuilder.withSquareOccupant(Square.fromCoords(4, 4), SquareOccupant.WR);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(boardBuilder.build(), Piece.ROOK);

        final List<Move> expectedMoves =
                new ArrayList<>(Arrays.asList());

        Collections.sort(expectedMoves);
        Collections.sort(actualMoves);

        assertEquals(expectedMoves, actualMoves);

    }

    @Test
    public void getQueenMoves() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());
        boardBuilder.withSquareOccupant(Square.fromCoords(4, 4), SquareOccupant.WQ);

        final List<Move> actualMoves =
                BoardUtils.getSliderMoves(boardBuilder.build(), Piece.QUEEN);

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
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSquareOccupant(Square.fromCoords(4,7), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(5,2), SquareOccupant.WK);
        boardBuilder.withSquareOccupant(Square.fromCoords(5,3), SquareOccupant.WB);
        boardBuilder.withSquareOccupant(Square.fromCoords(6,2), SquareOccupant.WN);

        final List<Move> actualMoves = BoardUtils.getKingMoves(boardBuilder.build());

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
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));
    }

    @Test
    public void testCastlingAvailableButBlocked() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());
        boardBuilder.withIsKingSideCastleAvailable(Colour.WHITE, true);
        boardBuilder.withIsKingSideCastleAvailable(Colour.BLACK, true);
        boardBuilder.withIsQueenSideCastleAvailable(Colour.WHITE, true);
        boardBuilder.withIsQueenSideCastleAvailable(Colour.BLACK, true);

        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));
    }

    @Test
    public void testWhiteKingSideCastling() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withIsKingSideCastleAvailable(Colour.WHITE, true);
        boardBuilder.withIsKingSideCastleAvailable(Colour.BLACK, true);
        boardBuilder.withIsQueenSideCastleAvailable(Colour.WHITE, true);
        boardBuilder.withIsQueenSideCastleAvailable(Colour.BLACK, true);

        boardBuilder.withSquareOccupant(Square.fromCoords(5, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(6, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList(
                new Move(Square.fromCoords(4,7), Square.fromCoords(6,7))
        )), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(7, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(7, 7), SquareOccupant.WR);
        boardBuilder.withSquareOccupant(Square.fromCoords(4, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));
    }

    @Test
    public void testWhiteQueenSideCastling() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withIsKingSideCastleAvailable(Colour.WHITE, true);
        boardBuilder.withIsKingSideCastleAvailable(Colour.BLACK, true);
        boardBuilder.withIsQueenSideCastleAvailable(Colour.WHITE, true);
        boardBuilder.withIsQueenSideCastleAvailable(Colour.BLACK, true);

        boardBuilder.withSquareOccupant(Square.fromCoords(3, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(2, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(1, 7), SquareOccupant.NONE);

        assertEquals(new ArrayList<>(Arrays.asList(
                new Move(Square.fromCoords(4,7), Square.fromCoords(2,7))
        )), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(0, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(0, 7), SquareOccupant.WR);
        boardBuilder.withSquareOccupant(Square.fromCoords(4, 7), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));
    }

    @Test
    public void testBlackKingSideCastling() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.BLACK);

        boardBuilder.withIsKingSideCastleAvailable(Colour.WHITE, true);
        boardBuilder.withIsKingSideCastleAvailable(Colour.BLACK, true);
        boardBuilder.withIsQueenSideCastleAvailable(Colour.WHITE, true);
        boardBuilder.withIsQueenSideCastleAvailable(Colour.BLACK, true);

        boardBuilder.withSquareOccupant(Square.fromCoords(5, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(6, 0), SquareOccupant.NONE);

        assertEquals(new ArrayList<>(Arrays.asList(
                new Move(Square.fromCoords(4,0), Square.fromCoords(6,0))
        )), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(7, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(7, 0), SquareOccupant.WR);
        boardBuilder.withSquareOccupant(Square.fromCoords(4, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));
    }

    @Test
    public void testBlackQueenSideCastling() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.BLACK);

        boardBuilder.withIsKingSideCastleAvailable(Colour.WHITE, true);
        boardBuilder.withIsKingSideCastleAvailable(Colour.BLACK, true);
        boardBuilder.withIsQueenSideCastleAvailable(Colour.WHITE, true);
        boardBuilder.withIsQueenSideCastleAvailable(Colour.BLACK, true);

        boardBuilder.withSquareOccupant(Square.fromCoords(3, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(2, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(1, 0), SquareOccupant.NONE);

        assertEquals(new ArrayList<>(Arrays.asList(
                new Move(Square.fromCoords(4,0), Square.fromCoords(2,0))
        )), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(0, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(0, 0), SquareOccupant.WR);
        boardBuilder.withSquareOccupant(Square.fromCoords(4, 0), SquareOccupant.NONE);
        assertEquals(new ArrayList<>(Arrays.asList()), BoardUtils.getCastlingMoves(boardBuilder.build()));
    }

    @Test
    public void getKingMovesWhenKingOnAnEdge() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSquareOccupant(Square.fromCoords(4,7), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(7,2), SquareOccupant.WK);

        final List<Move> actualMoves = BoardUtils.getKingMoves(boardBuilder.build());

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
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        final List<Move> actualMoves = BoardUtils.getKnightMoves(boardBuilder.build());

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
        final Board.BoardBuilder boardBuilder = Board.builder();

        boardBuilder.withSquareOccupant(Square.fromCoords(5, 6), SquareOccupant.BB);
        boardBuilder.withSideToMove(Colour.WHITE);

        final List<Square> actualSquares =
                BoardUtils.getDirectionalSquaresFromSquare(
                        Square.fromCoords(2,3),
                        SliderDirection.SE,
                        boardBuilder.build());

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
        final Board.BoardBuilder boardBuilder = Board.builder();

        boardBuilder.withSquareOccupant(Square.fromCoords(5, 6), SquareOccupant.BB);
        boardBuilder.withSideToMove(Colour.BLACK);

        final List<Square> actualSquares =
                BoardUtils.getDirectionalSquaresFromSquare(
                        Square.fromCoords(2,3), SliderDirection.SE, boardBuilder.build());

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
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSquareOccupant(Square.fromCoords(1,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(1,3), SquareOccupant.WP);

        boardBuilder.withSquareOccupant(Square.fromCoords(2,1), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(2,3), SquareOccupant.BP);

        boardBuilder.withSquareOccupant(Square.fromCoords(6,1), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(6,2), SquareOccupant.BP);

        boardBuilder.withSquareOccupant(Square.fromCoords(6,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(6,4), SquareOccupant.WP);

        boardBuilder.withSquareOccupant(Square.fromCoords(5,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(5,5), SquareOccupant.WP);

        boardBuilder.withSquareOccupant(Square.fromCoords(5,0), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(4,4), SquareOccupant.BB);

        boardBuilder.withSquareOccupant(Square.fromCoords(3,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(4,2), SquareOccupant.WP);

        boardBuilder.withSquareOccupant(Square.fromCoords(1,0), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(2,5), SquareOccupant.BN);

        boardBuilder.withSquareOccupant(Square.fromCoords(7,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(7,1), SquareOccupant.WP);

        boardBuilder.withEnPassantFile(2);

        final List<Move> actualMoves = BoardUtils.getPawnMoves(boardBuilder.build());

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
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSquareOccupant(Square.fromCoords(4,4), SquareOccupant.BP);
        boardBuilder.withSquareOccupant(Square.fromCoords(3,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(2,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(1,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(0,6), SquareOccupant.NONE);

        final List<Move> actualMoves = BoardUtils.getPawnMoves(boardBuilder.build());

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
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSideToMove(Colour.BLACK);

        boardBuilder.withSquareOccupant(Square.fromCoords(1,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(1,3), SquareOccupant.WP);

        boardBuilder.withSquareOccupant(Square.fromCoords(2,1), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(2,3), SquareOccupant.BP);

        boardBuilder.withSquareOccupant(Square.fromCoords(6,1), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(6,2), SquareOccupant.BP);

        boardBuilder.withSquareOccupant(Square.fromCoords(6,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(6,4), SquareOccupant.WP);

        boardBuilder.withSquareOccupant(Square.fromCoords(5,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(5,5), SquareOccupant.WP);

        boardBuilder.withSquareOccupant(Square.fromCoords(5,0), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(4,4), SquareOccupant.BB);

        boardBuilder.withSquareOccupant(Square.fromCoords(3,6), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(4,2), SquareOccupant.WP);

        boardBuilder.withSquareOccupant(Square.fromCoords(1,0), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(2,5), SquareOccupant.BN);

        boardBuilder.withSquareOccupant(Square.fromCoords(7,1), SquareOccupant.NONE);
        boardBuilder.withSquareOccupant(Square.fromCoords(7,6), SquareOccupant.BP);

        boardBuilder.withSquareOccupant(Square.fromCoords(7,7), SquareOccupant.NONE);

        boardBuilder.withEnPassantFile(6);

        final List<Move> actualMoves = BoardUtils.getPawnMoves(boardBuilder.build());

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
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());
        assertFalse(BoardUtils.isCheck(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(4,6), SquareOccupant.BR);
        assertTrue(BoardUtils.isCheck(boardBuilder.build()));

        boardBuilder.withSquareOccupant(Square.fromCoords(4,6), SquareOccupant.WR);
        assertFalse(BoardUtils.isCheck(boardBuilder.build()));

        boardBuilder.withSideToMove(Colour.BLACK);
        assertFalse(BoardUtils.isCheck(boardBuilder.build()));

    }

    @Test
    public void testRemoveChecksFromMoves() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder(CommonTestUtils.getStartBoard());

        boardBuilder.withSquareOccupant(Square.fromCoords(7,4), SquareOccupant.BB);
        assertFalse(BoardUtils.isCheck(boardBuilder.build()));

        assertEquals(19, BoardUtils.getAllMovesWithoutRemovingChecks(boardBuilder.build()).size());
        assertEquals(17, BoardUtils.getLegalMoves(boardBuilder.build()).size());
    }

    @Test
    public void testIsSquareAttacked() {
        final Board board = Board.fromFen("8/7p/p5pb/4k3/P1pPn3/8/P5PP/1rB2RK1 b - d3 0 28");

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,0), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,0), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,1), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,1), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,2), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,2), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,3), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,3), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,4), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,4), Colour.BLACK));

        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,5), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,5), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,6), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,6), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,7), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(0,7), Colour.BLACK));

        /*****************************************************************************************/

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,0), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,0), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,1), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,1), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,2), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,2), Colour.BLACK));

        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,3), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,3), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,4), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,4), Colour.BLACK));

        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,5), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,5), Colour.BLACK));

        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,6), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,6), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,7), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(1,7), Colour.BLACK));

        /*****************************************************************************************/

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,0), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,0), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,1), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,1), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,2), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,2), Colour.BLACK));

        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,3), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,3), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,4), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,4), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,5), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,5), Colour.BLACK));

        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,6), Colour.WHITE));
        assertFalse(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,6), Colour.BLACK));

        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,7), Colour.WHITE));
        assertTrue(BoardUtils.isSquareAttackedBy(board, Square.fromCoords(2,7), Colour.BLACK));
    }
}
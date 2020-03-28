package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.helper.CastlingHelper;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.helper.PawnMoveHelper;
import com.netsensia.rivalchess.model.Piece;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;

public class MoveMaker {

    private MoveMaker() {}

    public static Board makeMove(final Board board, final Move move) {

        final Board.BoardBuilder newBoardBuilder = new Board.BoardBuilder(board);

        final Square fromSquare = move.getSrcBoardRef();
        final Square toSquare = move.getTgtBoardRef();

        final SquareOccupant movingPiece = board.getSquareOccupant(fromSquare);

        disableCastleFlags(newBoardBuilder, fromSquare, board.getSideToMove());
        disableCastleFlags(newBoardBuilder, toSquare, board.getSideToMove().opponent());

        makeEnPassantMoves(board, newBoardBuilder, move);
        setEnPassantFile(move, newBoardBuilder, movingPiece);
        makeCastleMoves(board, newBoardBuilder, move);

        newBoardBuilder.withSquareOccupant(move.getTgtBoardRef(), movingPiece);
        makePromotionMoves(board, newBoardBuilder, move);
        newBoardBuilder.withSquareOccupant(move.getSrcBoardRef(), SquareOccupant.NONE);

        newBoardBuilder.withSideToMove(board.getSideToMove().opponent());

        return newBoardBuilder.build();
    }

    private static void disableCastleFlags(
            final Board.BoardBuilder boardBuilder,
            final Square square,
            final Colour colour) {

        if (square.equals(CastlingHelper.kingHome(colour))) {
            boardBuilder.withIsKingSideCastleAvailable(colour, false);
            boardBuilder.withIsQueenSideCastleAvailable(colour, false);
        }
        if (square.equals(CastlingHelper.queenRookHome(colour))) {
            boardBuilder.withIsQueenSideCastleAvailable(colour, false);
        }
        if (square.equals(CastlingHelper.kingRookHome(colour))) {
            boardBuilder.withIsKingSideCastleAvailable(colour, false);
        }
    }

    private static void setEnPassantFile(
            final Move move,
            final Board.BoardBuilder boardBuilder,
            final SquareOccupant movingPiece) {

        if (movingPiece.getPiece() == Piece.PAWN &&
                Math.abs(move.getTgtBoardRef().getYRank() - move.getSrcBoardRef().getYRank()) == 2) {
            boardBuilder.withEnPassantFile(move.getSrcBoardRef().getXFile());
        } else {
            boardBuilder.withEnPassantFile(-1);
        }
    }

    private static void makeCastleMoves(
            final Board board,
            final Board.BoardBuilder boardBuilder,
            final Move move) {

        if (board.getSquareOccupant(move.getSrcBoardRef()).getPiece() != Piece.KING) {
            return;
        }

        final Colour mover = board.getSideToMove();

        if (move.getSrcBoardRef().equals(CastlingHelper.kingHome(mover))) {
            if (move.getTgtBoardRef().equals(CastlingHelper.kingKnightHome(mover))) {
                boardBuilder.withSquareOccupant(CastlingHelper.kingBishopHome(mover), SquareOccupant.WR.ofColour(mover));
                boardBuilder.withSquareOccupant(CastlingHelper.kingRookHome(mover), SquareOccupant.NONE);
            } else if (move.getTgtBoardRef().equals(CastlingHelper.queenBishopHome(mover))) {
                boardBuilder.withSquareOccupant(CastlingHelper.queenHome(mover), SquareOccupant.WR.ofColour(mover));
                boardBuilder.withSquareOccupant(CastlingHelper.queenRookHome(mover), SquareOccupant.NONE);
            }
        }
    }

    private static void makePromotionMoves(
            final Board board,
            final Board.BoardBuilder boardBuilder,
            final Move move) {

        if (board.getSquareOccupant(move.getSrcBoardRef()).getPiece() != Piece.PAWN) {
            return;
        }

        if (move.getSrcBoardRef().getYRank() == PawnMoveHelper.promotionRank(board.getSideToMove())) {
            boardBuilder.withSquareOccupant(move.getTgtBoardRef(), move.getPromotedPiece());
        }
    }

    private static void makeEnPassantMoves(
            final Board board,
            final Board.BoardBuilder boardBuilder,
            final Move move) {

        if (board.getSquareOccupant(move.getSrcBoardRef()).getPiece() != Piece.PAWN ||
                move.getSrcBoardRef().getXFile() == move.getTgtBoardRef().getXFile() ||
                board.getSquareOccupant(move.getTgtBoardRef()) != SquareOccupant.NONE) {
            return;
        }

        final Square targetSquare = move.getTgtBoardRef();
        boardBuilder.withSquareOccupant(
                Square.fromCoords(
                        targetSquare.getXFile(),
                        targetSquare.getYRank() + PawnMoveHelper.advanceDirection(board.getSideToMove().opponent())),
                SquareOccupant.NONE);

    }
}

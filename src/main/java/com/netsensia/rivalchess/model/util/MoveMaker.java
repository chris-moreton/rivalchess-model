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

        final Board newBoard = Board.copy(board);

        final Square fromSquare = move.getSrcBoardRef();
        final Square toSquare = move.getTgtBoardRef();

        final SquareOccupant movingPiece = board.getSquareOccupant(fromSquare);

        disableCastleFlags(newBoard, fromSquare, board.getSideToMove());
        disableCastleFlags(newBoard, toSquare, board.getSideToMove().opponent());

        // Todo: this must be called before setSquareOccupant - not safe
        makeEnPassantMoves(newBoard, move);
        setEnPassantFile(move, newBoard, movingPiece);

        // Todo: this relies on makeEnPassantMoves having already been called - not safe
        newBoard.setSquareOccupant(move.getTgtBoardRef(), movingPiece);

        makeCastleMoves(newBoard, move);
        makePromotionMoves(newBoard, move);

        newBoard.setSquareOccupant(move.getSrcBoardRef(), SquareOccupant.NONE);

        newBoard.setSideToMove(board.getSideToMove().opponent());

        return newBoard;
    }

    private static void disableCastleFlags(Board board, Square square, Colour colour) {
        if (square.equals(CastlingHelper.kingHome(colour))) {
            board.setKingSideCastleAvailable(colour, false);
            board.setQueenSideCastleAvailable(colour, false);
        }
        if (square.equals(CastlingHelper.queenRookHome(colour))) {
            board.setQueenSideCastleAvailable(colour, false);
        }
        if (square.equals(CastlingHelper.kingRookHome(colour))) {
            board.setKingSideCastleAvailable(colour, false);
        }
    }

    private static void setEnPassantFile(Move move, Board newBoard, SquareOccupant movingPiece) {
        if (movingPiece.getPiece() == Piece.PAWN &&
                Math.abs(move.getTgtBoardRef().getYRank() - move.getSrcBoardRef().getYRank()) == 2) {
            newBoard.setEnPassantFile(move.getSrcBoardRef().getXFile());
        } else {
            newBoard.setEnPassantFile(-1);
        }
    }

    private static void makeCastleMoves(final Board board, final Move move) {

        if (board.getSquareOccupant(move.getSrcBoardRef()).getPiece() != Piece.KING) {
            return;
        }

        final Colour mover = board.getSideToMove();

        if (move.getSrcBoardRef().equals(CastlingHelper.kingHome(mover))) {
            if (move.getTgtBoardRef().equals(CastlingHelper.kingKnightHome(mover))) {
                board.setSquareOccupant(CastlingHelper.kingBishopHome(mover), SquareOccupant.WR.ofColour(mover));
                board.setSquareOccupant(CastlingHelper.kingRookHome(mover), SquareOccupant.NONE);
            } else if (move.getTgtBoardRef().equals(CastlingHelper.queenBishopHome(mover))) {
                board.setSquareOccupant(CastlingHelper.queenHome(mover), SquareOccupant.WR.ofColour(mover));
                board.setSquareOccupant(CastlingHelper.queenRookHome(mover), SquareOccupant.NONE);
            }
        }
    }

    private static void makePromotionMoves(Board board, Move move) {

        if (board.getSquareOccupant(move.getSrcBoardRef()).getPiece() != Piece.PAWN) {
            return;
        }

        if (move.getSrcBoardRef().getYRank() == PawnMoveHelper.promotionRank(board.getSideToMove())) {
            board.setSquareOccupant(move.getTgtBoardRef(), move.getPromotedPiece());
        }
    }

    private static void makeEnPassantMoves(Board board, Move move) {

        if (board.getSquareOccupant(move.getSrcBoardRef()).getPiece() != Piece.PAWN) {
            return;
        }

        if (move.getSrcBoardRef().getXFile() == move.getTgtBoardRef().getXFile()) {
            return;
        }

        if (board.getSquareOccupant(move.getTgtBoardRef()) != SquareOccupant.NONE) {
            return;
        }

        final Colour mover = board.getSideToMove();

        final Square targetSquare = move.getTgtBoardRef();
        board.setSquareOccupant(
                new Square(
                        targetSquare.getXFile(),
                        targetSquare.getYRank() + PawnMoveHelper.advanceDirection(mover.opponent())),
                SquareOccupant.NONE);

    }
}

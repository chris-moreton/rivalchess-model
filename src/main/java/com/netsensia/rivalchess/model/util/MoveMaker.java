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

    public static Board makeMove(final Board board, final Move move) {
        Board newBoard = Board.copy(board);

        newBoard.setSquareOccupant(move.getTgtBoardRef(), board.getSquareOccupant(move.getSrcBoardRef()));

        makeCastleMoves(newBoard, move);
        makePromotionMoves(newBoard, move);
        makeEnPassantMoves(newBoard, move);

        newBoard.setSquareOccupant(move.getSrcBoardRef(), SquareOccupant.NONE);

        newBoard.setSideToMove(board.getSideToMove().opponent());

        return newBoard;
    }

    private static void makeCastleMoves(Board board, Move move) {

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

        final Colour mover = board.getSideToMove();

        if (move.getSrcBoardRef().getYRank() == PawnMoveHelper.promotionRank(mover)) {
            board.setSquareOccupant(move.getTgtBoardRef(), move.getPromotedPiece());
        }
    }

    private static void makeEnPassantMoves(Board board, Move move) {
        if (board.getSquareOccupant(move.getSrcBoardRef()).getPiece() != Piece.PAWN) {
            return;
        }

        final Colour mover = board.getSideToMove();

        if (move.getSrcBoardRef().getYRank() == PawnMoveHelper.enPassantFromRank(mover)) {
            final Square targetSquare = move.getTgtBoardRef();
            board.setSquareOccupant(
                    new Square(
                            targetSquare.getXFile(),
                            targetSquare.getYRank() + PawnMoveHelper.advanceDirection(mover.opponent())),
                    SquareOccupant.NONE);
        }

    }
}

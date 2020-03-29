package com.netsensia.rivalchess.model.util

import com.netsensia.rivalchess.model.*
import com.netsensia.rivalchess.model.Board.BoardBuilder
import com.netsensia.rivalchess.model.helper.CastlingHelper
import com.netsensia.rivalchess.model.helper.PawnMoveHelper

object MoveMaker {
    @kotlin.jvm.JvmStatic
    fun makeMove(board: Board, move: Move): Board {
        val newBoardBuilder = BoardBuilder(board)
        val fromSquare = move.srcBoardRef
        val toSquare = move.tgtBoardRef
        val movingPiece = board.getSquareOccupant(fromSquare)
        disableCastleFlags(newBoardBuilder, fromSquare, board.sideToMove)
        disableCastleFlags(newBoardBuilder, toSquare, board.sideToMove.opponent())
        makeEnPassantMoves(board, newBoardBuilder, move)
        setEnPassantFile(move, newBoardBuilder, movingPiece)
        makeCastleMoves(board, newBoardBuilder, move)
        newBoardBuilder.withSquareOccupant(move.tgtBoardRef, movingPiece)
        makePromotionMoves(board, newBoardBuilder, move)
        newBoardBuilder.withSquareOccupant(move.srcBoardRef, SquareOccupant.NONE)
        newBoardBuilder.withSideToMove(board.sideToMove.opponent())
        return newBoardBuilder.build()
    }

    private fun disableCastleFlags(
            boardBuilder: BoardBuilder,
            square: Square,
            colour: Colour) {
        if (square == CastlingHelper.kingHome(colour)) {
            boardBuilder.withIsKingSideCastleAvailable(colour, false)
            boardBuilder.withIsQueenSideCastleAvailable(colour, false)
        }
        if (square == CastlingHelper.queenRookHome(colour)) {
            boardBuilder.withIsQueenSideCastleAvailable(colour, false)
        }
        if (square == CastlingHelper.kingRookHome(colour)) {
            boardBuilder.withIsKingSideCastleAvailable(colour, false)
        }
    }

    private fun setEnPassantFile(
            move: Move,
            boardBuilder: BoardBuilder,
            movingPiece: SquareOccupant) {
        if (movingPiece.piece == Piece.PAWN &&
                Math.abs(move.tgtBoardRef.yRank - move.srcBoardRef.yRank) == 2) {
            boardBuilder.withEnPassantFile(move.srcBoardRef.xFile)
        } else {
            boardBuilder.withEnPassantFile(-1)
        }
    }

    private fun makeCastleMoves(
            board: Board,
            boardBuilder: BoardBuilder,
            move: Move) {
        if (board.getSquareOccupant(move.srcBoardRef).piece != Piece.KING) {
            return
        }
        val mover = board.sideToMove
        if (move.srcBoardRef == CastlingHelper.kingHome(mover)) {
            if (move.tgtBoardRef == CastlingHelper.kingKnightHome(mover)) {
                boardBuilder.withSquareOccupant(CastlingHelper.kingBishopHome(mover), SquareOccupant.WR.ofColour(mover))
                boardBuilder.withSquareOccupant(CastlingHelper.kingRookHome(mover), SquareOccupant.NONE)
            } else if (move.tgtBoardRef == CastlingHelper.queenBishopHome(mover)) {
                boardBuilder.withSquareOccupant(CastlingHelper.queenHome(mover), SquareOccupant.WR.ofColour(mover))
                boardBuilder.withSquareOccupant(CastlingHelper.queenRookHome(mover), SquareOccupant.NONE)
            }
        }
    }

    private fun makePromotionMoves(
            board: Board,
            boardBuilder: BoardBuilder,
            move: Move) {
        if (board.getSquareOccupant(move.srcBoardRef).piece != Piece.PAWN) {
            return
        }
        if (move.srcBoardRef.yRank == PawnMoveHelper.promotionRank(board.sideToMove)) {
            boardBuilder.withSquareOccupant(move.tgtBoardRef, move.promotedPiece)
        }
    }

    private fun makeEnPassantMoves(
            board: Board,
            boardBuilder: BoardBuilder,
            move: Move) {
        if (board.getSquareOccupant(move.srcBoardRef).piece != Piece.PAWN || move.srcBoardRef.xFile == move.tgtBoardRef.xFile || board.getSquareOccupant(move.tgtBoardRef) != SquareOccupant.NONE) {
            return
        }
        val targetSquare = move.tgtBoardRef
        boardBuilder.withSquareOccupant(
                Square.Companion.fromCoords(
                        targetSquare.xFile,
                        targetSquare.yRank + PawnMoveHelper.advanceDirection(board.sideToMove.opponent())),
                SquareOccupant.NONE)
    }
}
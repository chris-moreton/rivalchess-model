package com.netsensia.rivalchess.model.util

import com.netsensia.rivalchess.model.*
import com.netsensia.rivalchess.model.Board.BoardBuilder
import com.netsensia.rivalchess.model.exception.InvalidBoardException
import com.netsensia.rivalchess.model.exception.ParallelProcessingException
import com.netsensia.rivalchess.model.helper.CastlingHelper
import com.netsensia.rivalchess.model.helper.KnightDirection
import com.netsensia.rivalchess.model.helper.PawnMoveHelper
import com.netsensia.rivalchess.model.helper.SliderDirection
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.stream.Collectors

object BoardUtils {
    @kotlin.jvm.JvmStatic
    fun getSliderMoves(board: Board, piece: Piece): List<Move> {
        val fromSquares = board.getSquaresWithOccupant(piece.toSquareOccupant(board.sideToMove))
        val moves: MutableList<Move> = ArrayList()
        for (fromSquare in fromSquares!!) {
            for (sliderDirection in SliderDirection.Companion.getDirectionsForPiece(piece)) {
                moves.addAll(
                        getDirectionalSquaresFromSquare(fromSquare, sliderDirection, board)
                                .stream()
                                .map { s: Square -> Move(fromSquare, s) }
                                .collect(Collectors.toList()))
            }
        }
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun getDirectionalSquaresFromSquare(
            square: Square,
            direction: SliderDirection,
            board: Board): MutableList<Square> {
        if (!square!!.isValidDirection(direction)) {
            return ArrayList()
        }
        val head = square.fromDirection(direction)
        val squareOccupant = board.getSquareOccupant(head)
        if (squareOccupant != SquareOccupant.NONE) {
            return if (squareOccupant.colour == board.sideToMove) ArrayList() else ArrayList(Arrays.asList(head))
        }
        val tail = getDirectionalSquaresFromSquare(head, direction, board)
        tail.add(head)
        return tail
    }

    @kotlin.jvm.JvmStatic
    fun getKnightMoves(board: Board): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        val fromSquares = board.getSquaresWithOccupant(
                Piece.KNIGHT.toSquareOccupant(board.sideToMove))
        for (fromSquare in fromSquares!!) {
            for (knightDirection in KnightDirection.values()) {
                if (fromSquare!!.isValidDirection(knightDirection)) {
                    val targetSquare = fromSquare.fromDirection(knightDirection)
                    val capturePiece = board.getSquareOccupant(targetSquare)
                    if (capturePiece == SquareOccupant.NONE || capturePiece.colour != board.sideToMove) {
                        moves.add(Move(fromSquare, targetSquare))
                    }
                }
            }
        }
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun getPawnMoves(board: Board): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        val mover = board.sideToMove
        val fromSquares = board.getSquaresWithOccupant(Piece.PAWN.toSquareOccupant(board.sideToMove))
        for (fromSquare in fromSquares!!) {
            val oneSquareForward: Square = Square.Companion.fromCoords(fromSquare.xFile,
                    fromSquare.yRank + PawnMoveHelper.advanceDirection(mover))
            if (board.getSquareOccupant(oneSquareForward) == SquareOccupant.NONE) {
                if (fromSquare.yRank == PawnMoveHelper.promotionRank(mover)) {
                    moves.addAll(getAllPromotionOptionsForMove(mover, fromSquare, oneSquareForward))
                } else {
                    moves.add(Move(fromSquare, oneSquareForward))
                }
                if (fromSquare.yRank == PawnMoveHelper.homeRank(mover)) {
                    val twoSquaresForward: Square = Square.Companion.fromCoords(fromSquare.xFile,
                            PawnMoveHelper.homeRank(mover) + PawnMoveHelper.advanceDirection(mover) * 2)
                    if (board.getSquareOccupant(twoSquaresForward) == SquareOccupant.NONE) {
                        moves.add(Move(fromSquare, twoSquaresForward))
                    }
                }
            }
            moves.addAll(getPawnCaptures(board, mover, fromSquare))
        }
        return moves
    }

    private fun getAllPromotionOptionsForMove(
            mover: Colour,
            fromSquare: Square,
            toSquare: Square): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        moves.add(Move(fromSquare, toSquare, SquareOccupant.WQ.ofColour(mover)))
        moves.add(Move(fromSquare, toSquare, SquareOccupant.WN.ofColour(mover)))
        moves.add(Move(fromSquare, toSquare, SquareOccupant.WB.ofColour(mover)))
        moves.add(Move(fromSquare, toSquare, SquareOccupant.WR.ofColour(mover)))
        return moves
    }

    private fun getPawnCaptures(
            board: Board,
            mover: Colour,
            fromSquare: Square): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        for (captureDirection in PawnMoveHelper.captureDirections) {
            moves.addAll(getPawnCapturesInDirection(board, mover, fromSquare, captureDirection))
        }
        return moves
    }

    private fun getPawnCapturesInDirection(
            board: Board,
            mover: Colour,
            fromSquare: Square,
            captureDirection: SliderDirection): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        val captureX = fromSquare.xFile + captureDirection.xIncrement
        val captureY = fromSquare.yRank + PawnMoveHelper.advanceDirection(mover)
        if (fromSquare!!.isValidDirection(captureDirection)) {
            val captureSquare: Square = Square.Companion.fromCoords(captureX, captureY)
            if (board.getSquareOccupant(captureSquare) == SquareOccupant.NONE) {
                if (board.enPassantFile == captureSquare.xFile &&
                        captureSquare.yRank == PawnMoveHelper.enPassantToRank(mover)) {
                    moves.add(Move(fromSquare, captureSquare))
                }
            } else if (board.getSquareOccupant(captureSquare).colour == mover!!.opponent()) {
                if (fromSquare.yRank == PawnMoveHelper.promotionRank(mover)) {
                    moves.addAll(getAllPromotionOptionsForMove(mover, fromSquare, captureSquare))
                } else {
                    moves.add(Move(fromSquare, captureSquare))
                }
            }
        }
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun getKingMoves(board: Board): List<Move> {
        val fromSquare = board.getSquaresWithOccupant(Piece.KING.toSquareOccupant(board.sideToMove))[0]
        val moves: MutableList<Move> = ArrayList()
        for (sliderDirection in SliderDirection.Companion.getDirectionsForPiece(Piece.KING)) {
            if (fromSquare!!.isValidDirection(sliderDirection)) {
                val targetSquare = fromSquare.fromDirection(sliderDirection)
                val capturePiece = board.getSquareOccupant(targetSquare)
                if (capturePiece == SquareOccupant.NONE || capturePiece.colour != board.sideToMove) {
                    moves.add(Move(fromSquare, targetSquare))
                }
            }
        }
        moves.addAll(getCastlingMoves(board))
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun getCastlingMoves(board: Board): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        val colour = board.sideToMove
        if (board.isKingSideCastleAvailable(colour) && board.getSquareOccupant(CastlingHelper.kingHome(colour)) == SquareOccupant.WK.ofColour(colour) && board.getSquareOccupant(CastlingHelper.kingRookHome(colour)) == SquareOccupant.WR.ofColour(colour) && board.getSquareOccupant(CastlingHelper.kingKnightHome(colour)) == SquareOccupant.NONE && board.getSquareOccupant(CastlingHelper.kingBishopHome(colour)) == SquareOccupant.NONE &&
                !isSquareAttackedBy(board, CastlingHelper.kingHome(colour), colour!!.opponent()) &&
                !isSquareAttackedBy(board, CastlingHelper.kingKnightHome(colour), colour.opponent()) &&
                !isSquareAttackedBy(board, CastlingHelper.kingBishopHome(colour), colour.opponent())) {
            moves.add(Move(CastlingHelper.kingHome(colour), CastlingHelper.kingKnightHome(colour)))
        }
        if (board.isQueenSideCastleAvailable(colour) && board.getSquareOccupant(CastlingHelper.kingHome(colour)) == SquareOccupant.WK.ofColour(colour) && board.getSquareOccupant(CastlingHelper.queenRookHome(colour)) == SquareOccupant.WR.ofColour(colour) && board.getSquareOccupant(CastlingHelper.queenHome(colour)) == SquareOccupant.NONE && board.getSquareOccupant(CastlingHelper.queenKnightHome(colour)) == SquareOccupant.NONE && board.getSquareOccupant(CastlingHelper.queenBishopHome(colour)) == SquareOccupant.NONE &&
                !isSquareAttackedBy(board, CastlingHelper.kingHome(colour), colour!!.opponent()) &&
                !isSquareAttackedBy(board, CastlingHelper.queenBishopHome(colour), colour.opponent()) &&
                !isSquareAttackedBy(board, CastlingHelper.queenHome(colour), colour.opponent())) {
            moves.add(Move(CastlingHelper.kingHome(colour), CastlingHelper.queenBishopHome(colour)))
        }
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun getAllMovesWithoutRemovingChecks(board: Board): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        moves.addAll(getPawnMoves(board))
        moves.addAll(getKnightMoves(board))
        moves.addAll(getKingMoves(board))
        moves.addAll(getSliderMoves(board, Piece.QUEEN))
        moves.addAll(getSliderMoves(board, Piece.BISHOP))
        moves.addAll(getSliderMoves(board, Piece.ROOK))
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun isSquareAttackedBy(board: Board, square: Square, byColour: Colour): Boolean {
        return isSquareAttackedByPawn(board, square, byColour) ||
                isSquareAttackedByBishopOrQueen(board, square, byColour) ||
                isSquareAttackedByRookOrQueen(board, square, byColour) ||
                isSquareAttackedByKing(board, square, byColour) ||
                isSquareAttackedByKnight(board, square, byColour)
    }

    private fun isSquareAttackedByKing(board: Board?, square: Square?, byColour: Colour?): Boolean {
        for (sliderDirection in SliderDirection.Companion.getDirectionsForPiece(Piece.KING)) {
            if (square!!.isValidDirection(sliderDirection) &&
                    board!!.getSquareOccupant(square.fromDirection(sliderDirection)) ==
                    SquareOccupant.WK.ofColour(byColour)) {
                return true
            }
        }
        return false
    }

    private fun isSquareAttackedByKnight(board: Board?, square: Square?, byColour: Colour?): Boolean {
        for (knightDirection in KnightDirection.values()) {
            if (square!!.isValidDirection(knightDirection) &&
                    board!!.getSquareOccupant(square.fromDirection(knightDirection)) ==
                    SquareOccupant.WN.ofColour(byColour)) {
                return true
            }
        }
        return false
    }

    private fun isSquareAttackedByPawn(board: Board, square: Square, byColour: Colour): Boolean {
        for (captureDirection in PawnMoveHelper.captureDirections) {
            val newX = square.xFile + captureDirection.xIncrement
            val newY = square.yRank + PawnMoveHelper.advanceDirection(byColour.opponent())
            if (CommonUtils.isValidSquareReference(newX, newY)
                    && board.getSquareOccupant(Square.Companion.fromCoords(newX, newY)) == SquareOccupant.WP.ofColour(byColour)) {
                return true
            }
        }
        return false
    }

    private fun isSquareAttackedByBishopOrQueen(board: Board?, square: Square?, byColour: Colour?): Boolean {
        for (sliderDirection in SliderDirection.Companion.getDirectionsForPiece(Piece.BISHOP)) {
            if (isSquareAttackedBySliderInDirection(board, square, byColour, sliderDirection, Piece.BISHOP)) {
                return true
            }
        }
        return false
    }

    private fun isSquareAttackedByRookOrQueen(board: Board?, square: Square?, byColour: Colour?): Boolean {
        for (sliderDirection in SliderDirection.Companion.getDirectionsForPiece(Piece.ROOK)) {
            if (isSquareAttackedBySliderInDirection(board, square, byColour, sliderDirection, Piece.ROOK)) {
                return true
            }
        }
        return false
    }

    private fun isSquareAttackedBySliderInDirection(
            board: Board?,
            square: Square?,
            byColour: Colour?,
            sliderDirection: SliderDirection,
            piece: Piece): Boolean {
        if (square!!.isValidDirection(sliderDirection)) {
            val newSquare = square.fromDirection(sliderDirection)
            val squareOccupant = board!!.getSquareOccupant(newSquare)
            if (squareOccupant == SquareOccupant.NONE) {
                return isSquareAttackedBySliderInDirection(board, newSquare, byColour, sliderDirection, piece)
            }
            if (squareOccupant == piece.toSquareOccupant(byColour) ||
                    squareOccupant == SquareOccupant.WQ.ofColour(byColour)) {
                return true
            }
        }
        return false
    }

    fun isMoveLeavesMoverInCheck(board: Board, move: Move): Boolean {
        val boardBuilder = BoardBuilder(MoveMaker.makeMove(board, move))
                .withSideToMove(board.sideToMove)
        return try {
            isCheck(boardBuilder!!.build())
        } catch (e: InvalidBoardException) {
            throw InvalidBoardException("After making trial move " + move + ", I caught " + e.message)
        }
    }

    @kotlin.jvm.JvmStatic
    fun isCheck(board: Board?): Boolean {
        val squares = board!!.getSquaresWithOccupant(
                SquareOccupant.WK.ofColour(board.sideToMove))
        if (squares!!.isEmpty()) {
            throw InvalidBoardException(
                    """Given a board with no ${board.sideToMove} king on it.
$board""")
        }
        val ourKingSquare = squares[0]
        return isSquareAttackedBy(board, ourKingSquare, board.sideToMove.opponent())
    }

    @kotlin.jvm.JvmStatic
    fun getLegalMoves(board: Board): List<Move> {
        return getAllMovesWithoutRemovingChecks(board)
                .parallelStream()
                .filter { m: Move -> !isMoveLeavesMoverInCheck(board, m) }
                .collect(Collectors.toList())
    }
}
package com.netsensia.rivalchess.model.util

import com.netsensia.rivalchess.model.*
import com.netsensia.rivalchess.model.Board.BoardBuilder
import com.netsensia.rivalchess.model.exception.InvalidBoardException
import com.netsensia.rivalchess.model.helper.CastlingHelper
import com.netsensia.rivalchess.model.helper.KnightDirection
import com.netsensia.rivalchess.model.helper.PawnMoveHelper
import com.netsensia.rivalchess.model.helper.SliderDirection
import java.util.*
import java.util.stream.Collectors

object BoardUtils {
    @kotlin.jvm.JvmStatic
    fun Board.getSliderMoves(piece: Piece): List<Move> {
        val fromSquares = getSquaresWithOccupant(piece.toSquareOccupant(sideToMove))
        val moves: MutableList<Move> = ArrayList()
        for (fromSquare in fromSquares) {
            for (sliderDirection in SliderDirection.getDirectionsForPiece(piece)) {
                moves.addAll(
                        getDirectionalSquaresFromSquare(fromSquare, sliderDirection)
                                .stream()
                                .map { s: Square -> Move(fromSquare, s) }
                                .collect(Collectors.toList()))
            }
        }
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun Board.getDirectionalSquaresFromSquare(
            square: Square,
            direction: SliderDirection): MutableList<Square> {
        if (!square.isValidDirection(direction)) {
            return ArrayList()
        }
        val head = square.fromDirection(direction)
        val squareOccupant = getSquareOccupant(head)
        if (squareOccupant != SquareOccupant.NONE) {
            return if (squareOccupant.colour == sideToMove) ArrayList() else ArrayList(listOf(head))
        }
        val tail = getDirectionalSquaresFromSquare(head, direction)
        tail.add(head)
        return tail
    }

    @kotlin.jvm.JvmStatic
    fun Board.getKnightMoves(): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        val fromSquares = getSquaresWithOccupant(
                Piece.KNIGHT.toSquareOccupant(sideToMove))
        for (fromSquare in fromSquares) {
            for (knightDirection in KnightDirection.values()) {
                if (fromSquare.isValidDirection(knightDirection)) {
                    val targetSquare = fromSquare.fromDirection(knightDirection)
                    val capturePiece = getSquareOccupant(targetSquare)
                    if (capturePiece == SquareOccupant.NONE || capturePiece.colour != sideToMove) {
                        moves.add(Move(fromSquare, targetSquare))
                    }
                }
            }
        }
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun Board.getPawnMoves(): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        val mover = sideToMove
        val fromSquares = getSquaresWithOccupant(Piece.PAWN.toSquareOccupant(sideToMove))
        for (fromSquare in fromSquares) {
            val oneSquareForward: Square = Square.fromCoords(fromSquare.xFile,
                    fromSquare.yRank + PawnMoveHelper.advanceDirection(mover))
            if (getSquareOccupant(oneSquareForward) == SquareOccupant.NONE) {
                if (fromSquare.yRank == PawnMoveHelper.promotionRank(mover)) {
                    moves.addAll(getAllPromotionOptionsForMove(mover, fromSquare, oneSquareForward))
                } else {
                    moves.add(Move(fromSquare, oneSquareForward))
                }
                if (fromSquare.yRank == PawnMoveHelper.homeRank(mover)) {
                    val twoSquaresForward: Square = Square.fromCoords(fromSquare.xFile,
                            PawnMoveHelper.homeRank(mover) + PawnMoveHelper.advanceDirection(mover) * 2)
                    if (getSquareOccupant(twoSquaresForward) == SquareOccupant.NONE) {
                        moves.add(Move(fromSquare, twoSquaresForward))
                    }
                }
            }
            moves.addAll(getPawnCaptures(mover, fromSquare))
        }
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun getAllPromotionOptionsForMove(
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

    @kotlin.jvm.JvmStatic
    fun Board.getPawnCaptures(
            mover: Colour,
            fromSquare: Square
    ): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        for (captureDirection in PawnMoveHelper.captureDirections) {
            moves.addAll(getPawnCapturesInDirection(mover, fromSquare, captureDirection))
        }
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun Board.getPawnCapturesInDirection(
            mover: Colour,
            fromSquare: Square,
            captureDirection: SliderDirection): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        val captureX = fromSquare.xFile + captureDirection.xIncrement
        val captureY = fromSquare.yRank + PawnMoveHelper.advanceDirection(mover)
        if (fromSquare.isValidDirection(captureDirection)) {
            val captureSquare: Square = Square.fromCoords(captureX, captureY)
            if (getSquareOccupant(captureSquare) == SquareOccupant.NONE) {
                if (enPassantFile == captureSquare.xFile &&
                        captureSquare.yRank == PawnMoveHelper.enPassantToRank(mover)) {
                    moves.add(Move(fromSquare, captureSquare))
                }
            } else if (getSquareOccupant(captureSquare).colour == mover.opponent()) {
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
    fun Board.getKingMoves(): List<Move> {
        val fromSquare = getSquaresWithOccupant(Piece.KING.toSquareOccupant(sideToMove))[0]
        val moves: MutableList<Move> = ArrayList()
        for (sliderDirection in SliderDirection.getDirectionsForPiece(Piece.KING)) {
            if (fromSquare.isValidDirection(sliderDirection)) {
                val targetSquare = fromSquare.fromDirection(sliderDirection)
                val capturePiece = getSquareOccupant(targetSquare)
                if (capturePiece == SquareOccupant.NONE || capturePiece.colour != sideToMove) {
                    moves.add(Move(fromSquare, targetSquare))
                }
            }
        }
        moves.addAll(getCastlingMoves())
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun Board.getCastlingMoves(): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        val colour = sideToMove
        if (isKingSideCastleAvailable(colour) && getSquareOccupant(CastlingHelper.kingHome(colour)) == SquareOccupant.WK.ofColour(colour) && getSquareOccupant(CastlingHelper.kingRookHome(colour)) == SquareOccupant.WR.ofColour(colour) && getSquareOccupant(CastlingHelper.kingKnightHome(colour)) == SquareOccupant.NONE && getSquareOccupant(CastlingHelper.kingBishopHome(colour)) == SquareOccupant.NONE &&
                !isSquareAttackedBy(CastlingHelper.kingHome(colour), colour.opponent()) &&
                !isSquareAttackedBy(CastlingHelper.kingKnightHome(colour), colour.opponent()) &&
                !isSquareAttackedBy(CastlingHelper.kingBishopHome(colour), colour.opponent())) {
            moves.add(Move(CastlingHelper.kingHome(colour), CastlingHelper.kingKnightHome(colour)))
        }
        if (isQueenSideCastleAvailable(colour) && getSquareOccupant(CastlingHelper.kingHome(colour)) == SquareOccupant.WK.ofColour(colour) && getSquareOccupant(CastlingHelper.queenRookHome(colour)) == SquareOccupant.WR.ofColour(colour) && getSquareOccupant(CastlingHelper.queenHome(colour)) == SquareOccupant.NONE && getSquareOccupant(CastlingHelper.queenKnightHome(colour)) == SquareOccupant.NONE && getSquareOccupant(CastlingHelper.queenBishopHome(colour)) == SquareOccupant.NONE &&
                !isSquareAttackedBy(CastlingHelper.kingHome(colour), colour.opponent()) &&
                !isSquareAttackedBy(CastlingHelper.queenBishopHome(colour), colour.opponent()) &&
                !isSquareAttackedBy(CastlingHelper.queenHome(colour), colour.opponent())) {
            moves.add(Move(CastlingHelper.kingHome(colour), CastlingHelper.queenBishopHome(colour)))
        }
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun Board.getAllMovesWithoutRemovingChecks(): List<Move> {
        val moves: MutableList<Move> = ArrayList()
        moves.addAll(getPawnMoves())
        moves.addAll(getKnightMoves())
        moves.addAll(getKingMoves())
        moves.addAll(getSliderMoves(Piece.QUEEN))
        moves.addAll(getSliderMoves(Piece.BISHOP))
        moves.addAll(getSliderMoves(Piece.ROOK))
        return moves
    }

    @kotlin.jvm.JvmStatic
    fun Board.isSquareAttackedBy(square: Square, byColour: Colour): Boolean {
        return isSquareAttackedByPawn(square, byColour) ||
                isSquareAttackedByBishopOrQueen(square, byColour) ||
                isSquareAttackedByRookOrQueen(square, byColour) ||
                isSquareAttackedByKing(square, byColour) ||
                isSquareAttackedByKnight(square, byColour)
    }

    @kotlin.jvm.JvmStatic
    fun Board.isSquareAttackedByKing(square: Square, byColour: Colour): Boolean {
        for (sliderDirection in SliderDirection.getDirectionsForPiece(Piece.KING)) {
            if (square.isValidDirection(sliderDirection) &&
                    getSquareOccupant(square.fromDirection(sliderDirection)) ==
                    SquareOccupant.WK.ofColour(byColour)) {
                return true
            }
        }
        return false
    }

    @kotlin.jvm.JvmStatic
    fun Board.isSquareAttackedByKnight(square: Square, byColour: Colour): Boolean {
        for (knightDirection in KnightDirection.values()) {
            if (square.isValidDirection(knightDirection) &&
                    getSquareOccupant(square.fromDirection(knightDirection)) ==
                    SquareOccupant.WN.ofColour(byColour)) {
                return true
            }
        }
        return false
    }

    @kotlin.jvm.JvmStatic
    fun Board.isSquareAttackedByPawn(square: Square, byColour: Colour): Boolean {
        for (captureDirection in PawnMoveHelper.captureDirections) {
            val newX = square.xFile + captureDirection.xIncrement
            val newY = square.yRank + PawnMoveHelper.advanceDirection(byColour.opponent())
            if (CommonUtils.isValidSquareReference(newX, newY)
                    && getSquareOccupant(Square.fromCoords(newX, newY)) == SquareOccupant.WP.ofColour(byColour)) {
                return true
            }
        }
        return false
    }

    @kotlin.jvm.JvmStatic
    fun Board.isSquareAttackedByBishopOrQueen(square: Square, byColour: Colour): Boolean {
        for (sliderDirection in SliderDirection.getDirectionsForPiece(Piece.BISHOP)) {
            if (isSquareAttackedBySliderInDirection(square, byColour, sliderDirection, Piece.BISHOP)) {
                return true
            }
        }
        return false
    }

    @kotlin.jvm.JvmStatic
    fun Board.isSquareAttackedByRookOrQueen(square: Square, byColour: Colour): Boolean {
        for (sliderDirection in SliderDirection.getDirectionsForPiece(Piece.ROOK)) {
            if (isSquareAttackedBySliderInDirection(square, byColour, sliderDirection, Piece.ROOK)) {
                return true
            }
        }
        return false
    }

    @kotlin.jvm.JvmStatic
    fun Board.isSquareAttackedBySliderInDirection(
            square: Square,
            byColour: Colour,
            sliderDirection: SliderDirection,
            piece: Piece
    ): Boolean {

        if (square.isValidDirection(sliderDirection)) {
            val newSquare = square.fromDirection(sliderDirection)
            val squareOccupant = getSquareOccupant(newSquare)
            if (squareOccupant == SquareOccupant.NONE) {
                return isSquareAttackedBySliderInDirection(newSquare, byColour, sliderDirection, piece)
            }
            if (squareOccupant == piece.toSquareOccupant(byColour) ||
                    squareOccupant == SquareOccupant.WQ.ofColour(byColour)) {
                return true
            }
        }

        return false
    }

    @kotlin.jvm.JvmStatic
    fun Board.isMoveLeavesMoverInCheck(move: Move): Boolean {
        return try {
            BoardBuilder(MoveMaker.makeMove(this, move))
                    .withSideToMove(sideToMove)
                    .build().isCheck()
        } catch (e: InvalidBoardException) {
            throw InvalidBoardException("After making trial move " + move + ", I caught " + e.message)
        }
    }

    @kotlin.jvm.JvmStatic
    fun Board.isCheck(): Boolean {
        val squares = getSquaresWithOccupant(
                SquareOccupant.WK.ofColour(sideToMove))
        if (squares.isEmpty()) {
            throw InvalidBoardException("Given a board with no $sideToMove king on it. $this")
        }
        val ourKingSquare = squares[0]
        return isSquareAttackedBy(ourKingSquare, sideToMove.opponent())
    }

    @kotlin.jvm.JvmStatic
    fun Board.getLegalMoves(): List<Move> {
        return getAllMovesWithoutRemovingChecks()
                .parallelStream()
                .filter { m: Move -> !isMoveLeavesMoverInCheck(m) }
                .collect(Collectors.toList())
    }
}
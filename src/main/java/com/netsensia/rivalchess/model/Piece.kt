package com.netsensia.rivalchess.model

import com.netsensia.rivalchess.model.exception.BadPieceConversionException

enum class Piece(val index: Int) {
    NONE(0), PAWN(1), KNIGHT(2), ROOK(3), KING(4), QUEEN(5), BISHOP(6);

    fun toSquareOccupant(colour: Colour?): SquareOccupant {
        return when (this) {
            PAWN -> if (colour == Colour.WHITE) SquareOccupant.WP else SquareOccupant.BP
            KNIGHT -> if (colour == Colour.WHITE) SquareOccupant.WN else SquareOccupant.BN
            BISHOP -> if (colour == Colour.WHITE) SquareOccupant.WB else SquareOccupant.BB
            ROOK -> if (colour == Colour.WHITE) SquareOccupant.WR else SquareOccupant.BR
            QUEEN -> if (colour == Colour.WHITE) SquareOccupant.WQ else SquareOccupant.BQ
            KING -> if (colour == Colour.WHITE) SquareOccupant.WK else SquareOccupant.BK
            else -> throw BadPieceConversionException("Can't create squareOccupant from $this and $colour")
        }
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromSquareOccupant(squareOccupant: SquareOccupant?): Piece {
            return when (squareOccupant) {
                SquareOccupant.WP, SquareOccupant.BP -> PAWN
                SquareOccupant.WR, SquareOccupant.BR -> ROOK
                SquareOccupant.WB, SquareOccupant.BB -> BISHOP
                SquareOccupant.WN, SquareOccupant.BN -> KNIGHT
                SquareOccupant.WQ, SquareOccupant.BQ -> QUEEN
                SquareOccupant.WK, SquareOccupant.BK -> KING
                else -> NONE
            }
        }
    }

}
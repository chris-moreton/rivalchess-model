package com.netsensia.rivalchess.model

import com.netsensia.rivalchess.model.exception.EnumConversionException

enum class SquareOccupant(val index: Int) {
    NONE(-1), WP(0), WN(1), WB(2), WQ(3), WK(4), WR(5), BP(6), BN(7), BB(8), BQ(9), BK(10), BR(11);

    fun ofColour(colour: Colour?): SquareOccupant {
        return when (colour) {
            Colour.WHITE -> when (this) {
                WP, BP -> WP
                WR, BR -> WR
                WQ, BQ -> WQ
                WB, BB -> WB
                WK, BK -> WK
                WN, BN -> WN
                else -> NONE
            }
            Colour.BLACK -> when (this) {
                WP, BP -> BP
                WR, BR -> BR
                WQ, BQ -> BQ
                WB, BB -> BB
                WK, BK -> BK
                WN, BN -> BN
                else -> NONE
            }
            else -> throw EnumConversionException("Invalid colour")
        }
    }

    val colour: Colour
        get() = when (this) {
            NONE -> throw EnumConversionException("CCan't get piece colour of an empty square")
            WP, WK, WN, WB, WR, WQ -> Colour.WHITE
            BP, BK, BN, BB, BR, BQ -> Colour.BLACK
        }

    val piece: Piece
        get() = when (this) {
            NONE -> Piece.NONE
            WP, BP -> Piece.PAWN
            WK, BK -> Piece.KING
            WR, BR -> Piece.ROOK
            WB, BB -> Piece.BISHOP
            WN, BN -> Piece.KNIGHT
            WQ, BQ -> Piece.QUEEN
        }

    fun toChar(): Char {
        return when (this) {
            NONE -> '-'
            WP -> 'P'
            BP -> 'p'
            WK -> 'K'
            BK -> 'k'
            WR -> 'R'
            BR -> 'r'
            WB -> 'B'
            BB -> 'b'
            WN -> 'N'
            BN -> 'n'
            WQ -> 'Q'
            BQ -> 'q'
            else -> throw EnumConversionException("Invalid SquareOccupant")
        }
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromChar(piece: Char): SquareOccupant {
            return when (piece) {
                'p' -> BP
                'q' -> BQ
                'r' -> BR
                'n' -> BN
                'b' -> BB
                'k' -> BK
                'P' -> WP
                'Q' -> WQ
                'R' -> WR
                'N' -> WN
                'B' -> WB
                'K' -> WK
                else -> NONE
            }
        }
    }

}
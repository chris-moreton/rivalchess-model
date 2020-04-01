package com.netsensia.rivalchess.model.helper

import com.netsensia.rivalchess.model.Piece
import com.netsensia.rivalchess.model.exception.MoveGenerationException
import java.util.*

enum class SliderDirection(val xIncrement: Int, val yIncrement: Int) {
    N(0, -1),
    NE(1, -1),
    E(1, 0),
    SE(1, 1),
    S(0, 1),
    SW(-1, 1),
    W(-1, 0),
    NW(-1, -1);

    companion object {
        private val queenDirections: List<SliderDirection> = ArrayList(listOf(NE, NW, SE, SW, N, W, S, E))
        private val rookDirections: List<SliderDirection> = ArrayList(listOf(N, W, S, E))
        private val bishopDirections: List<SliderDirection> = ArrayList(listOf(NE, NW, SE, SW))

        fun getDirectionsForPiece(piece: Piece): List<SliderDirection> {
            return when (piece) {
                Piece.KING, Piece.QUEEN -> queenDirections
                Piece.BISHOP -> bishopDirections
                Piece.ROOK -> rookDirections
                else -> throw MoveGenerationException("Can't get directions for a non-sliding piece")
            }
        }
    }

}
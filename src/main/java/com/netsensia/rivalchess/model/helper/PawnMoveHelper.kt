package com.netsensia.rivalchess.model.helper

import com.netsensia.rivalchess.model.Colour
import java.util.*

object PawnMoveHelper {
    val captureDirections = Arrays.asList(SliderDirection.E, SliderDirection.W)

    fun homeRank(colour: Colour?): Int {
        return if (colour == Colour.WHITE) 6 else 1
    }

    fun enPassantFromRank(colour: Colour): Int {
        return if (colour == Colour.WHITE) 3 else 4
    }

    fun enPassantToRank(colour: Colour?): Int {
        return if (colour == Colour.WHITE) 2 else 5
    }

    fun promotionRank(colour: Colour?): Int {
        return if (colour == Colour.WHITE) 1 else 6
    }

    fun advanceDirection(colour: Colour?): Int {
        return if (colour == Colour.WHITE) -1 else 1
    }
}
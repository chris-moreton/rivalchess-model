package com.netsensia.rivalchess.model.helper

import com.netsensia.rivalchess.model.Colour
import com.netsensia.rivalchess.model.Square

object CastlingHelper {
    fun queenRookHome(colour: Colour?): Square {
        return Square.Companion.fromCoords(0, if (colour == Colour.WHITE) 7 else 0)
    }

    fun queenKnightHome(colour: Colour?): Square {
        return Square.Companion.fromCoords(1, if (colour == Colour.WHITE) 7 else 0)
    }

    fun queenBishopHome(colour: Colour?): Square {
        return Square.Companion.fromCoords(2, if (colour == Colour.WHITE) 7 else 0)
    }

    fun queenHome(colour: Colour?): Square {
        return Square.Companion.fromCoords(3, if (colour == Colour.WHITE) 7 else 0)
    }

    fun kingHome(colour: Colour?): Square {
        return Square.Companion.fromCoords(4, if (colour == Colour.WHITE) 7 else 0)
    }

    fun kingBishopHome(colour: Colour?): Square {
        return Square.Companion.fromCoords(5, if (colour == Colour.WHITE) 7 else 0)
    }

    fun kingKnightHome(colour: Colour?): Square {
        return Square.Companion.fromCoords(6, if (colour == Colour.WHITE) 7 else 0)
    }

    fun kingRookHome(colour: Colour?): Square {
        return Square.Companion.fromCoords(7, if (colour == Colour.WHITE) 7 else 0)
    }
}
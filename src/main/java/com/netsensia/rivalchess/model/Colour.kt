package com.netsensia.rivalchess.model

import java.util.*

enum class Colour(val value: Int) {
    WHITE(0), BLACK(1);

    fun opponent(): Colour {
        return if (this == WHITE) BLACK else WHITE
    }

    companion object {
        fun list(): List<Colour> {
            return ArrayList(Arrays.asList(WHITE, BLACK))
        }
    }

}
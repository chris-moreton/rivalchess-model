package com.netsensia.rivalchess.model

import java.util.*

enum class Colour(val value: Char) {
    WHITE('w'), BLACK('b');

    fun opponent(): Colour {
        return if (this == WHITE) BLACK else WHITE
    }

    companion object {
        fun list(): List<Colour> {
            return ArrayList(listOf(WHITE, BLACK))
        }
    }

}
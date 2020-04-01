package com.netsensia.rivalchess.model.helper

enum class KnightDirection(val xIncrement: Int, val yIncrement: Int) {
    NE(1, -2),
    NW(-1, -2),
    EN(2, -1),
    ES(2, 1),
    SE(1, 2),
    SW(-1, 2),
    WN(-2, -1),
    WS(-2, 1),
    ;
}
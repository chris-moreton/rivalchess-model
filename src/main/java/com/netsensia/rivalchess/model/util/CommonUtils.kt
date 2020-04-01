package com.netsensia.rivalchess.model.util

object CommonUtils {
    @kotlin.jvm.JvmStatic
    fun isValidRankFileBoardReference(n: Int): Boolean {
        return n in 0..7
    }

    fun isValidSquareReference(x: Int, y: Int): Boolean {
        return isValidRankFileBoardReference(x) && isValidRankFileBoardReference(y)
    }
}
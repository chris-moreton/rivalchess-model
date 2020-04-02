package com.netsensia.rivalchess.model

import com.netsensia.rivalchess.model.exception.EnumConversionException
import com.netsensia.rivalchess.model.exception.InvalidAlgebraicSquareException
import com.netsensia.rivalchess.model.helper.KnightDirection
import com.netsensia.rivalchess.model.helper.SliderDirection
import com.netsensia.rivalchess.model.util.CommonUtils
import java.util.*

enum class Square(val xFile: Int, val yRank: Int) {
    A8(0, 0), B8(1, 0), C8(2, 0), D8(3, 0), E8(4, 0), F8(5, 0), G8(6, 0), H8(7, 0), A7(0, 1), B7(1, 1), C7(2, 1), D7(3, 1), E7(4, 1), F7(5, 1), G7(6, 1), H7(7, 1), A6(0, 2), B6(1, 2), C6(2, 2), D6(3, 2), E6(4, 2), F6(5, 2), G6(6, 2), H6(7, 2), A5(0, 3), B5(1, 3), C5(2, 3), D5(3, 3), E5(4, 3), F5(5, 3), G5(6, 3), H5(7, 3), A4(0, 4), B4(1, 4), C4(2, 4), D4(3, 4), E4(4, 4), F4(5, 4), G4(6, 4), H4(7, 4), A3(0, 5), B3(1, 5), C3(2, 5), D3(3, 5), E3(4, 5), F3(5, 5), G3(6, 5), H3(7, 5), A2(0, 6), B2(1, 6), C2(2, 6), D2(3, 6), E2(4, 6), F2(5, 6), G2(6, 6), H2(7, 6), A1(0, 7), B1(1, 7), C1(2, 7), D1(3, 7), E1(4, 7), F1(5, 7), G1(6, 7), H1(7, 7);

    companion object {
        private val validSliderDirections: MutableMap<Square, MutableList<SliderDirection>> = EnumMap(Square::class.java)
        private val validKnightDirections: MutableMap<Square, MutableList<KnightDirection>> = EnumMap(Square::class.java)

        private fun directionIsValid(square: Square, direction: SliderDirection): Boolean {
            return CommonUtils.isValidRankFileBoardReference(square.xFile + direction.xIncrement) &&
                    CommonUtils.isValidRankFileBoardReference(square.yRank + direction.yIncrement)
        }

        private fun knightDirectionIsValid(square: Square, direction: KnightDirection): Boolean {
            return CommonUtils.isValidRankFileBoardReference(square.xFile + direction.xIncrement) &&
                    CommonUtils.isValidRankFileBoardReference(square.yRank + direction.yIncrement)
        }

        @kotlin.jvm.JvmStatic
        fun fromCoords(x: Int, y: Int): Square {
            return when (y * 8 + x) {
                0 -> A8
                1 -> B8
                2 -> C8
                3 -> D8
                4 -> E8
                5 -> F8
                6 -> G8
                7 -> H8
                8 -> A7
                9 -> B7
                10 -> C7
                11 -> D7
                12 -> E7
                13 -> F7
                14 -> G7
                15 -> H7
                16 -> A6
                17 -> B6
                18 -> C6
                19 -> D6
                20 -> E6
                21 -> F6
                22 -> G6
                23 -> H6
                24 -> A5
                25 -> B5
                26 -> C5
                27 -> D5
                28 -> E5
                29 -> F5
                30 -> G5
                31 -> H5
                32 -> A4
                33 -> B4
                34 -> C4
                35 -> D4
                36 -> E4
                37 -> F4
                38 -> G4
                39 -> H4
                40 -> A3
                41 -> B3
                42 -> C3
                43 -> D3
                44 -> E3
                45 -> F3
                46 -> G3
                47 -> H3
                48 -> A2
                49 -> B2
                50 -> C2
                51 -> D2
                52 -> E2
                53 -> F2
                54 -> G2
                55 -> H2
                56 -> A1
                57 -> B1
                58 -> C1
                59 -> D1
                60 -> E1
                61 -> F1
                62 -> G1
                63 -> H1
                else -> throw EnumConversionException("Invalid Square conversion")
            }
        }

        @kotlin.jvm.JvmStatic
        fun fromAlgebraic(algebraic: String): Square {
            if (algebraic.length != 2) {
                throw InvalidAlgebraicSquareException("Invalid algebraic square $algebraic")
            }
            val x = (algebraic.toCharArray()[0] - 97).toInt()
            val y: Int = 8 - (algebraic.toCharArray()[1] - 48).toInt()
            if (x < 0 || x > 7 || y < 0 || y > 7) {
                throw InvalidAlgebraicSquareException("Invalid algebraic square $algebraic")
            }
            return fromCoords(x, y)
        }

        init {
            for (square in values()) {
                if (!validSliderDirections.containsKey(square)) {
                    validSliderDirections[square] = ArrayList()
                }
                for (sliderDirection in SliderDirection.values()) {
                    if (directionIsValid(square, sliderDirection)) {
                        val sliderDirectionList = validSliderDirections[square]!!
                        sliderDirectionList.add(sliderDirection)
                    }
                }
            }
        }

        init {
            for (square in values()) {
                if (!validKnightDirections.containsKey(square)) {
                    validKnightDirections[square] = ArrayList()
                }
                for (knightDirection in KnightDirection.values()) {
                    if (knightDirectionIsValid(square, knightDirection)) {
                        val knightDirectionList = validKnightDirections[square]!!
                        knightDirectionList.add(knightDirection)
                    }
                }
            }
        }
    }

    fun isValidDirection(sliderDirection: SliderDirection): Boolean {
        return validSliderDirections[this]!!.contains(sliderDirection)
    }

    fun isValidDirection(knightDirection: KnightDirection): Boolean {
        return validKnightDirections[this]!!.contains(knightDirection)
    }

    fun fromDirection(sliderDirection: SliderDirection): Square {
        val newX = xFile + sliderDirection.xIncrement
        val newY = yRank + sliderDirection.yIncrement
        return fromCoords(newX, newY)
    }

    fun fromDirection(sliderDirection: SliderDirection, count: Int): Square {
        val newX = xFile + sliderDirection.xIncrement * count
        val newY = yRank + sliderDirection.yIncrement * count
        return fromCoords(newX, newY)
    }

    fun fromDirection(knightDirection: KnightDirection): Square {
        val newX = xFile + knightDirection.xIncrement
        val newY = yRank + knightDirection.yIncrement
        return fromCoords(newX, newY)
    }

    val algebraicXFile: Char
        get() = (97 + xFile).toChar()

    val algebraicYRank: Char
        get() = Character.forDigit(8 - yRank, 10)

    val algebraic: String
        get() = "" + algebraicXFile + algebraicYRank

}
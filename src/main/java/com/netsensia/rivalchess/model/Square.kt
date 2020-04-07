package com.netsensia.rivalchess.model

import com.netsensia.rivalchess.model.exception.EnumConversionException
import com.netsensia.rivalchess.model.exception.InvalidAlgebraicSquareException
import com.netsensia.rivalchess.model.helper.KnightDirection
import com.netsensia.rivalchess.model.helper.SliderDirection
import com.netsensia.rivalchess.model.util.CommonUtils
import java.util.*

enum class Square(val xFile: Int, val yRank: Int, val bitRef: Int) {
    A8(0, 0, 63),
    B8(1, 0, 62),
    C8(2, 0, 61),
    D8(3, 0, 60),
    E8(4, 0, 59),
    F8(5, 0, 58),
    G8(6, 0, 57),
    H8(7, 0, 56),
    A7(0, 1, 55),
    B7(1, 1, 54),
    C7(2, 1, 53),
    D7(3, 1, 52),
    E7(4, 1, 51),
    F7(5, 1, 50),
    G7(6, 1, 49),
    H7(7, 1, 48),
    A6(0, 2, 47),
    B6(1, 2, 46),
    C6(2, 2, 45),
    D6(3, 2, 44),
    E6(4, 2, 43),
    F6(5, 2, 42),
    G6(6, 2, 41),
    H6(7, 2, 40),
    A5(0, 3, 39),
    B5(1, 3, 38),
    C5(2, 3, 37),
    D5(3, 3, 36),
    E5(4, 3, 35),
    F5(5, 3, 34),
    G5(6, 3, 33),
    H5(7, 3, 32),
    A4(0, 4, 31),
    B4(1, 4, 30),
    C4(2, 4, 29),
    D4(3, 4, 28),
    E4(4, 4, 27),
    F4(5, 4, 26),
    G4(6, 4, 25),
    H4(7, 4, 24),
    A3(0, 5, 23),
    B3(1, 5, 22),
    C3(2, 5, 21),
    D3(3, 5, 20),
    E3(4, 5, 19),
    F3(5, 5, 18),
    G3(6, 5, 17),
    H3(7, 5, 16),
    A2(0, 6, 15),
    B2(1, 6, 14),
    C2(2, 6, 13),
    D2(3, 6, 12),
    E2(4, 6, 11),
    F2(5, 6, 10),
    G2(6, 6, 9),
    H2(7, 6, 8),
    A1(0, 7, 7),
    B1(1, 7, 6),
    C1(2, 7, 5),
    D1(3, 7, 4),
    E1(4, 7, 3),
    F1(5, 7, 2),
    G1(6, 7, 1),
    H1(7, 7, 0);

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
        fun fromBitRef(bitRef: Int): Square {
            return when (bitRef) {
                63 -> A8
                62 -> B8
                61 -> C8
                60 -> D8
                59 -> E8
                58 -> F8
                57 -> G8
                56 -> H8
                55 -> A7
                54 -> B7
                53 -> C7
                52 -> D7
                51 -> E7
                50 -> F7
                49 -> G7
                48 -> H7
                47 -> A6
                46 -> B6
                45 -> C6
                44 -> D6
                43 -> E6
                42 -> F6
                41 -> G6
                40 -> H6
                39 -> A5
                38 -> B5
                37 -> C5
                36 -> D5
                35 -> E5
                34 -> F5
                33 -> G5
                32 -> H5
                31 -> A4
                30 -> B4
                29 -> C4
                28 -> D4
                27 -> E4
                26 -> F4
                25 -> G4
                24 -> H4
                23 -> A3
                22 -> B3
                21 -> C3
                20 -> D3
                19 -> E3
                18 -> F3
                17 -> G3
                16 -> H3
                15 -> A2
                14 -> B2
                13 -> C2
                12 -> D2
                11 -> E2
                10 -> F2
                9 -> G2
                8 -> H2
                7 -> A1
                6 -> B1
                5 -> C1
                4 -> D1
                3 -> E1
                2 -> F1
                1 -> G1
                0 -> H1
                else -> throw EnumConversionException("Invalid BitRef for Square: ${bitRef}")
            }
        }

        @kotlin.jvm.JvmStatic
        fun fromCoords(x: Int, y: Int): Square {
            try {
                return fromBitRef(63 - (y * 8 + x))
            } catch (e: EnumConversionException) {
                throw EnumConversionException("Invalid Coords for Square: ($x,$y)")
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
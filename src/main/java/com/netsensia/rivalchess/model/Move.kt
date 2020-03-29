package com.netsensia.rivalchess.model

import com.netsensia.rivalchess.model.exception.InvalidSimpleAlgebraicMoveException

class Move : Comparable<Any?> {
    private val srcXFile: Int
    private val srcYRank: Int
    private val tgtXFile: Int
    private val tgtYRank: Int
    val promotedPiece: SquareOccupant

    constructor(
            srcBoardRef: Square,
            tgtBoardRef: Square
    ) : this(
            srcBoardRef.xFile,
            srcBoardRef.yRank,
            tgtBoardRef.xFile,
            tgtBoardRef.yRank,
            SquareOccupant.NONE) {
    }

    constructor(
            srcBoardRef: Square,
            tgtBoardRef: Square,
            promotionPiece: SquareOccupant
    ) : this(
            srcBoardRef.xFile,
            srcBoardRef.yRank,
            tgtBoardRef.xFile,
            tgtBoardRef.yRank,
            promotionPiece) {
    }

    @Deprecated("")
    constructor(
            srcXFile: Int,
            srcYRank: Int,
            tgtXFile: Int,
            tgtYRank: Int
    ) : this(srcXFile, srcYRank, tgtXFile, tgtYRank, SquareOccupant.NONE) {
    }

    @Deprecated("")
    constructor(
            srcXFile: Int,
            srcYRank: Int,
            tgtXFile: Int,
            tgtYRank: Int,
            promotionPiece: SquareOccupant
    ) {
        this.srcXFile = srcXFile
        this.srcYRank = srcYRank
        this.tgtXFile = tgtXFile
        this.tgtYRank = tgtYRank
        promotedPiece = promotionPiece
    }

    constructor(algebraic: String) {
        if (algebraic.length < 4 || algebraic.length > 5) {
            throw InvalidSimpleAlgebraicMoveException("Algebraic move must be four or five characters")
        }
        val from: Square = Square.Companion.fromAlgebraic(algebraic.substring(0, 2))
        val to: Square = Square.Companion.fromAlgebraic(algebraic.substring(2, 4))
        val promotionPiece = if (algebraic.length == 5) SquareOccupant.Companion.fromChar(algebraic.toCharArray()[4]) else SquareOccupant.NONE
        srcXFile = from.xFile
        srcYRank = from.yRank
        tgtXFile = to.xFile
        tgtYRank = to.yRank
        promotedPiece = promotionPiece
    }

    val srcBoardRef: Square
        get() = Square.Companion.fromCoords(srcXFile, srcYRank)

    val tgtBoardRef: Square
        get() = Square.Companion.fromCoords(tgtXFile, tgtYRank)

    override fun toString(): String {
        return srcBoardRef.algebraic +
                tgtBoardRef.algebraic +
                if (promotedPiece == SquareOccupant.NONE) "" else promotedPiece!!.toChar()
    }

    override fun equals(o: Any?): Boolean {
        if (o is Move) {
            val move = o
            return move.srcBoardRef == srcBoardRef && move.tgtBoardRef == tgtBoardRef && move.promotedPiece == promotedPiece
        }
        return false
    }

    override fun hashCode(): Int {
        return this.toString().hashCode()
    }

    override fun compareTo(o: Any?): Int {
        return this.toString().compareTo(o.toString())
    }
}
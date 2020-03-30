package com.netsensia.rivalchess.model

import com.netsensia.rivalchess.model.util.BoardUtils
import com.netsensia.rivalchess.model.util.FenUtils
import com.netsensia.rivalchess.model.util.MoveMaker
import java.util.*

class Board {
    private val squareOccupants: Map<Square, SquareOccupant>
    private val occupantSquares: MutableMap<SquareOccupant, MutableList<Square>>
    val enPassantFile: Int
    private val isWhiteKingSideCastleAvailable: Boolean
    private val isWhiteQueenSideCastleAvailable: Boolean
    private val isBlackKingSideCastleAvailable: Boolean
    private val isBlackQueenSideCastleAvailable: Boolean
    val halfMoveCount: Int
    val sideToMove: Colour
    val fullMoveCount: Int

    constructor(builder: BoardBuilder) {
        squareOccupants = EnumMap(builder.squareOccupants)
        enPassantFile = builder.enPassantFile
        isWhiteQueenSideCastleAvailable = builder.isWhiteQueenSideCastleAvailable
        isBlackQueenSideCastleAvailable = builder.isBlackQueenSideCastleAvailable
        isWhiteKingSideCastleAvailable = builder.isWhiteKingSideCastleAvailable
        isBlackKingSideCastleAvailable = builder.isBlackKingSideCastleAvailable
        halfMoveCount = builder.halfMoveCount
        sideToMove = builder.sideToMove
        fullMoveCount = builder.fullMoveCount
        occupantSquares = EnumMap(SquareOccupant::class.java)
        populateOccupantSquares()
    }

    fun getSquareOccupants(): Map<Square, SquareOccupant> {
        return EnumMap(squareOccupants)
    }

    constructor(board: Board) {
        squareOccupants = EnumMap(board.getSquareOccupants())
        sideToMove = board.sideToMove
        enPassantFile = board.enPassantFile
        isBlackKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.BLACK)
        isBlackQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.BLACK)
        isWhiteKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.WHITE)
        isWhiteQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.WHITE)
        halfMoveCount = board.halfMoveCount
        fullMoveCount = board.fullMoveCount
        occupantSquares = EnumMap(SquareOccupant::class.java)
        populateOccupantSquares()
    }

    private fun populateOccupantSquares() {
        for (so in SquareOccupant.values()) {
            occupantSquares[so] = ArrayList()
        }
        for (s in Square.values()) {
            val so = squareOccupants[s]
            if (so != SquareOccupant.NONE) {
                occupantSquares[so]!!.add(s)
            }
        }
    }

    fun getSquareOccupant(square: Square): SquareOccupant {
        return squareOccupants[square]!!
    }

    fun isKingSideCastleAvailable(colour: Colour): Boolean {
        return if (colour == Colour.WHITE) isWhiteKingSideCastleAvailable else isBlackKingSideCastleAvailable
    }

    fun isQueenSideCastleAvailable(colour: Colour): Boolean {
        return if (colour == Colour.WHITE) isWhiteQueenSideCastleAvailable else isBlackQueenSideCastleAvailable
    }

    fun getSquaresWithOccupant(squareOccupant: SquareOccupant): List<Square> {
        return ArrayList(occupantSquares[squareOccupant])
    }

    val legalMoves: List<Move>
        get() = BoardUtils.getLegalMoves(this)

    override fun equals(other: Any?): Boolean {
        if (other is Board) {
            val bo = other
            for (square in Square.values()) {
                if (getSquareOccupant(square) != bo.getSquareOccupant(square)) {
                    return false
                }
            }
            return enPassantFile == bo.enPassantFile &&
                    sideToMove == bo.sideToMove &&
                    halfMoveCount == bo.halfMoveCount &&
                    fullMoveCount == bo.fullMoveCount &&
                    isKingSideCastleAvailable(Colour.WHITE) == bo.isKingSideCastleAvailable(Colour.WHITE) &&
                    isKingSideCastleAvailable(Colour.BLACK) == bo.isKingSideCastleAvailable(Colour.BLACK) &&
                    isQueenSideCastleAvailable(Colour.WHITE) == bo.isQueenSideCastleAvailable(Colour.WHITE) &&
                    isQueenSideCastleAvailable(Colour.BLACK) == bo.isQueenSideCastleAvailable(Colour.BLACK)
        }
        return false
    }

    override fun hashCode(): Int {
        return this.toString().hashCode()
    }

    override fun toString(): String {
        val s = StringBuilder()
        s.append(squareOccupants)
        s.append("Castle privileges: " +
                isWhiteKingSideCastleAvailable +
                isWhiteQueenSideCastleAvailable +
                isBlackKingSideCastleAvailable +
                isBlackQueenSideCastleAvailable)
        s.append("\n")
        s.append("En passant file: $enPassantFile")
        s.append("\n")
        s.append("Half move count: $halfMoveCount")
        return s.toString()
    }

    class BoardBuilder {
        var squareOccupants: EnumMap<Square, SquareOccupant>
        var enPassantFile: Int
        var isWhiteKingSideCastleAvailable: Boolean
        var isWhiteQueenSideCastleAvailable: Boolean
        var isBlackKingSideCastleAvailable: Boolean
        var isBlackQueenSideCastleAvailable: Boolean
        var halfMoveCount: Int
        var fullMoveCount = 0
        var sideToMove: Colour

        constructor() {
            enPassantFile = -1
            isBlackQueenSideCastleAvailable = false
            isWhiteQueenSideCastleAvailable = false
            isWhiteKingSideCastleAvailable = false
            isBlackKingSideCastleAvailable = false
            halfMoveCount = 0
            sideToMove = Colour.WHITE
            squareOccupants = EnumMap(Square::class.java)
            for (square in Square.values()) {
                squareOccupants[square] = SquareOccupant.NONE
            }
        }

        constructor(board: Board) {
            squareOccupants = EnumMap(board.getSquareOccupants())
            enPassantFile = board.enPassantFile
            isWhiteKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.WHITE)
            isBlackKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.BLACK)
            isWhiteQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.WHITE)
            isBlackQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.BLACK)
            halfMoveCount = board.halfMoveCount
            sideToMove = board.sideToMove
            fullMoveCount = board.fullMoveCount
        }

        fun withSquareOccupant(square: Square, squareOccupant: SquareOccupant) = apply {
            squareOccupants[square] = squareOccupant
        }

        fun withEnPassantFile(enPassantFile: Int) = apply {
            this.enPassantFile = enPassantFile
        }

        fun withFullMoveCount(fullMoveCount: Int) = apply {
            this.fullMoveCount = fullMoveCount
        }

        fun withHalfMoveCount(halfMoveCount: Int) = apply {
            this.halfMoveCount = halfMoveCount
            return this
        }

        fun withSideToMove(sideToMove: Colour) = apply {
            this.sideToMove = sideToMove
        }

        fun withIsWhiteKingSideCastleAvailable(isWhiteKingSideCastleAvailable: Boolean) = apply {
            this.isWhiteKingSideCastleAvailable = isWhiteKingSideCastleAvailable
        }

        fun withIsBlackKingSideCastleAvailable(isBlackKingSideCastleAvailable: Boolean) = apply {
            this.isBlackKingSideCastleAvailable = isBlackKingSideCastleAvailable
        }

        fun withIsWhiteQueenSideCastleAvailable(isWhiteQueenSideCastleAvailable: Boolean) = apply {
            this.isWhiteQueenSideCastleAvailable = isWhiteQueenSideCastleAvailable
        }

        fun withIsBlackQueenSideCastleAvailable(isBlackQueenSideCastleAvailable: Boolean) = apply {
            this.isBlackQueenSideCastleAvailable = isBlackQueenSideCastleAvailable
        }

        fun withIsQueenSideCastleAvailable(colour: Colour, isQueenSideCastleAvailable: Boolean) = apply {
            if (colour == Colour.WHITE) {
                isWhiteQueenSideCastleAvailable = isQueenSideCastleAvailable
            } else {
                isBlackQueenSideCastleAvailable = isQueenSideCastleAvailable
            }
            return this
        }

        fun withIsKingSideCastleAvailable(colour: Colour, isKingSideCastleAvailable: Boolean) = apply {
            if (colour == Colour.WHITE) {
                isWhiteKingSideCastleAvailable = isKingSideCastleAvailable
            } else {
                isBlackKingSideCastleAvailable = isKingSideCastleAvailable
            }
        }

        fun withNoCastleFlags() = apply {
            isWhiteQueenSideCastleAvailable = false
            isBlackQueenSideCastleAvailable = false
            isWhiteKingSideCastleAvailable = false
            isBlackKingSideCastleAvailable = false
        }

        fun build(): Board {
            return Board(this)
        }
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromFen(fen: String): Board {
            return FenUtils.getBoardModel(fen)
        }

        @kotlin.jvm.JvmStatic
        fun fromMove(board: Board, move: Move): Board {
            return MoveMaker.makeMove(board, move)
        }

        @kotlin.jvm.JvmStatic
        fun builder(): BoardBuilder {
            return BoardBuilder()
        }
    }
}
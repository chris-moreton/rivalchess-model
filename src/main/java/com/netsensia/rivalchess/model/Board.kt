package com.netsensia.rivalchess.model

import com.netsensia.rivalchess.model.util.BoardUtils
import com.netsensia.rivalchess.model.util.FenUtils
import com.netsensia.rivalchess.model.util.MoveMaker
import java.util.*
import java.util.stream.Stream

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

    fun squareOccupantStream(): Stream<Map.Entry<Square, SquareOccupant>> {
        return squareOccupants.entries.stream()
    }

    fun getSquaresWithOccupant(squareOccupant: SquareOccupant): List<Square> {
        return ArrayList(occupantSquares[squareOccupant])
    }

    val legalMoves: List<Move>
        get() = BoardUtils.getLegalMoves(this)

    val isCheck: Boolean
        get() = BoardUtils.isCheck(this)

    fun isSquareAttackedBy(square: Square, byColour: Colour): Boolean {
        return BoardUtils.isSquareAttackedBy(this, square, byColour)
    }

    override fun equals(o: Any?): Boolean {
        if (o is Board) {
            val bo = o
            for (square in Square.values()) {
                if (getSquareOccupant(square) != bo.getSquareOccupant(square)) {
                    return false
                }
            }
            return enPassantFile == bo.enPassantFile && sideToMove == bo.sideToMove && halfMoveCount == bo.halfMoveCount && fullMoveCount == bo.fullMoveCount && isKingSideCastleAvailable(Colour.WHITE) == bo.isKingSideCastleAvailable(Colour.WHITE) && isKingSideCastleAvailable(Colour.BLACK) == bo.isKingSideCastleAvailable(Colour.BLACK) && isQueenSideCastleAvailable(Colour.WHITE) == bo.isQueenSideCastleAvailable(Colour.WHITE) && isQueenSideCastleAvailable(Colour.BLACK) == bo.isQueenSideCastleAvailable(Colour.BLACK)
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

        fun withSquareOccupant(square: Square, squareOccupant: SquareOccupant): BoardBuilder {
            squareOccupants[square] = squareOccupant
            return this
        }

        fun withEnPassantFile(enPassantFile: Int): BoardBuilder {
            this.enPassantFile = enPassantFile
            return this
        }

        fun withFullMoveCount(fullMoveCount: Int): BoardBuilder {
            this.fullMoveCount = fullMoveCount
            return this
        }

        fun withHalfMoveCount(halfMoveCount: Int): BoardBuilder {
            this.halfMoveCount = halfMoveCount
            return this
        }

        fun withSideToMove(sideToMove: Colour): BoardBuilder {
            this.sideToMove = sideToMove
            return this
        }

        fun withIsWhiteKingSideCastleAvailable(isWhiteKingSideCastleAvailable: Boolean): BoardBuilder {
            this.isWhiteKingSideCastleAvailable = isWhiteKingSideCastleAvailable
            return this
        }

        fun withIsBlackKingSideCastleAvailable(isBlackKingSideCastleAvailable: Boolean): BoardBuilder {
            this.isBlackKingSideCastleAvailable = isBlackKingSideCastleAvailable
            return this
        }

        fun withIsWhiteQueenSideCastleAvailable(isWhiteQueenSideCastleAvailable: Boolean): BoardBuilder {
            this.isWhiteQueenSideCastleAvailable = isWhiteQueenSideCastleAvailable
            return this
        }

        fun withIsBlackQueenSideCastleAvailable(isBlackQueenSideCastleAvailable: Boolean): BoardBuilder {
            this.isBlackQueenSideCastleAvailable = isBlackQueenSideCastleAvailable
            return this
        }

        fun withIsQueenSideCastleAvailable(colour: Colour, isQueenSideCastleAvailable: Boolean): BoardBuilder {
            if (colour == Colour.WHITE) {
                isWhiteQueenSideCastleAvailable = isQueenSideCastleAvailable
            } else {
                isBlackQueenSideCastleAvailable = isQueenSideCastleAvailable
            }
            return this
        }

        fun withIsKingSideCastleAvailable(colour: Colour, isKingSideCastleAvailable: Boolean): BoardBuilder {
            if (colour == Colour.WHITE) {
                isWhiteKingSideCastleAvailable = isKingSideCastleAvailable
            } else {
                isBlackKingSideCastleAvailable = isKingSideCastleAvailable
            }
            return this
        }

        fun withNoCastleFlags(): BoardBuilder {
            isWhiteQueenSideCastleAvailable = false
            isBlackQueenSideCastleAvailable = false
            isWhiteKingSideCastleAvailable = false
            isBlackKingSideCastleAvailable = false
            return this
        }

        fun build(): Board {
            return Board(this)
        }
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromFen(fen: String): Board? {
            return FenUtils.getBoardModel(fen)
        }

        @kotlin.jvm.JvmStatic
        fun fromMove(board: Board, move: Move): Board? {
            return MoveMaker.makeMove(board, move)
        }

        @kotlin.jvm.JvmStatic
        fun builder(): BoardBuilder {
            return BoardBuilder()
        }
    }
}
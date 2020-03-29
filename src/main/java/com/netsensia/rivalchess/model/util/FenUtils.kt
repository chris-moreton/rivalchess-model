package com.netsensia.rivalchess.model.util

import com.netsensia.rivalchess.model.Board
import com.netsensia.rivalchess.model.Board.BoardBuilder
import com.netsensia.rivalchess.model.Colour
import com.netsensia.rivalchess.model.Square
import com.netsensia.rivalchess.model.SquareOccupant
import com.netsensia.rivalchess.model.exception.IllegalFenException
import kotlin.system.exitProcess

object FenUtils {
    const val startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

    @kotlin.jvm.JvmStatic
    fun getBoardModel(fen: String): Board? {
        val fenParts = fen.trim().split(" ").toTypedArray()
        if (fenParts.size < 4) {
            throw IllegalFenException(
                    "Expected at least 2 sections to FEN - board and mover"
            )
        }
        val boardBuilder: BoardBuilder = Board.Companion.builder()
        setBoardParts(boardBuilder, fenParts[0])
        setMover(boardBuilder, fenParts[1])
        setCastleFlags(boardBuilder, if (fenParts.size > 2) fenParts[2] else "kqKQ")
        setEnPassant(boardBuilder, if (fenParts.size > 3) fenParts[3] else "-")
        setHalfMoves(boardBuilder, if (fenParts.size > 4) fenParts[4] else "0")
        setFullMoves(boardBuilder, if (fenParts.size > 5) fenParts[5] else "0")
        return boardBuilder.build()
    }

    private fun setMover(boardBuilder: BoardBuilder, boardPart: String) {
        if (boardPart.length != 1) {
            throw IllegalFenException("Unexpected error processing side to move")
        }
        val mover = boardPart.toCharArray()[0]
        when (mover) {
            'w' -> boardBuilder.withSideToMove(Colour.WHITE)
            'b' -> boardBuilder.withSideToMove(Colour.BLACK)
            else -> throw IllegalFenException("Invalid side to move: $mover")
        }
    }

    private fun setEnPassant(boardBuilder: BoardBuilder, boardPart: String) {
        if (boardPart == "-") {
            boardBuilder.withEnPassantFile(-1)
            return
        }
        if (boardPart.length != 2) {
            throw IllegalFenException("Unexpected error processing en passant part: $boardPart")
        }
        val enPassantFileAlgebraic = boardPart.toCharArray()[0]
        if (enPassantFileAlgebraic < 'a' || enPassantFileAlgebraic > 'h') {
            throw IllegalFenException("Invalid en passant part: $boardPart")
        }
        boardBuilder.withEnPassantFile(enPassantFileAlgebraic - 'a')
    }

    private fun setCastleFlags(boardBuilder: BoardBuilder, boardPart: String) {
        boardBuilder.withIsWhiteQueenSideCastleAvailable(boardPart.contains("Q"))
        boardBuilder.withIsWhiteKingSideCastleAvailable(boardPart.contains("K"))
        boardBuilder.withIsBlackQueenSideCastleAvailable(boardPart.contains("q"))
        boardBuilder.withIsBlackKingSideCastleAvailable(boardPart.contains("k"))
    }

    private fun setHalfMoves(boardBuilder: BoardBuilder, boardPart: String) {
        boardBuilder.withHalfMoveCount(boardPart.toInt())
    }

    private fun setFullMoves(boardBuilder: BoardBuilder, boardPart: String) {
        boardBuilder.withFullMoveCount(boardPart.toInt())
    }

    private fun setBoardParts(boardBuilder: BoardBuilder, boardPart: String) {
        val rankParts = boardPart.split("/").toTypedArray()
        if (rankParts.size != 8) {
            throw IllegalFenException(
                    "Expected 8 ranks in board part of FEN - found " + rankParts.size
            )
        }
        for (i in 0..7) {
            rankParts[i] = expand(rankParts[i])
        }
        getBoardFromRank(boardBuilder, rankParts, 0)
    }

    private fun expand(rankPart: String): String {
        return rankPart
                .replace("1", "-")
                .replace("2", "--")
                .replace("3", "---")
                .replace("4", "----")
                .replace("5", "-----")
                .replace("6", "------")
                .replace("7", "-------")
                .replace("8", "--------")
    }

    private fun getBoardFromRank(
            boardBuilder: BoardBuilder, rankParts: Array<String>, rank: Int) {
        if (rank < 8) {
            collectRankParts(boardBuilder, rankParts, 0, rank)
            getBoardFromRank(boardBuilder, rankParts, rank + 1)
        }
    }

    fun collectRankParts(
            boardBuilder: BoardBuilder, rankParts: Array<String>, file: Int, rank: Int) {
        if (file == 8) {
            return
        }
        val piece = rankParts[rank].toCharArray()[file]
        when (piece) {
            'p' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.BP)
            'P' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.WP)
            'q' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.BQ)
            'Q' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.WQ)
            'r' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.BR)
            'R' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.WR)
            'b' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.BB)
            'B' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.WB)
            'n' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.BN)
            'N' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.WN)
            'k' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.BK)
            'K' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.WK)
            '-' -> boardBuilder.withSquareOccupant(Square.Companion.fromCoords(file, rank), SquareOccupant.NONE)
            else -> throw IllegalFenException("Unexpected character in FEN: $piece")
        }
        collectRankParts(boardBuilder, rankParts, file + 1, rank)
    }

    @kotlin.jvm.JvmStatic
    fun invertFen(fen: String): String {
        var fen = fen
        fen = fen.trim { it <= ' ' }
        fen = fen.replace(" b ", " . ")
        fen = fen.replace(" w ", " ; ")
        fen = fen.replace('Q', 'z')
        fen = fen.replace('K', 'x')
        fen = fen.replace('N', 'c')
        fen = fen.replace('B', 'v')
        fen = fen.replace('R', 'm')
        fen = fen.replace('P', ',')
        fen = fen.replace('q', 'Q')
        fen = fen.replace('k', 'K')
        fen = fen.replace('n', 'N')
        fen = fen.replace('b', 'B')
        fen = fen.replace('r', 'R')
        fen = fen.replace('p', 'P')
        fen = fen.replace('z', 'q')
        fen = fen.replace('x', 'k')
        fen = fen.replace('c', 'n')
        fen = fen.replace('v', 'b')
        fen = fen.replace('m', 'r')
        fen = fen.replace(',', 'p')
        fen = fen.replace(" . ", " w ")
        fen = fen.replace(" ; ", " b ")
        val fenParts = fen.split(" ").toTypedArray()
        val boardParts = fenParts[0].split("/").toTypedArray()
        val newFen = boardParts[7] + "/" +
                boardParts[6] + "/" +
                boardParts[5] + "/" +
                boardParts[4] + "/" +
                boardParts[3] + "/" +
                boardParts[2] + "/" +
                boardParts[1] + "/" +
                boardParts[0]
        val newFenBuilder = StringBuilder(newFen)
        for (i in 1 until fenParts.size) {
            if (i == 3) {
                newFenBuilder.append(" ").append(invertSquare(fenParts[i]))
            } else {
                newFenBuilder.append(" ").append(fenParts[i])
            }
        }
        return newFenBuilder.toString()
    }

    private fun invertSquare(square: String): String {
        if (square == "-") {
            return square
        }
        if (square.length != 2) {
            throw IllegalFenException("Invalid square reference $square")
        }
        val file = square[0]
        val rank = square[1]
        val newFile = ('h' - file + 'a'.toInt()).toChar()
        val newRank = ('8' - rank + '1'.toInt()).toChar()
        return newFile.toString() + newRank
    }
}
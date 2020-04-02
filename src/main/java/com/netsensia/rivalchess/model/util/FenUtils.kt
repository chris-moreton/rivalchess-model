package com.netsensia.rivalchess.model.util

import com.netsensia.rivalchess.model.Board
import com.netsensia.rivalchess.model.Board.BoardBuilder
import com.netsensia.rivalchess.model.Colour
import com.netsensia.rivalchess.model.Square
import com.netsensia.rivalchess.model.SquareOccupant
import com.netsensia.rivalchess.model.exception.IllegalFenException
import com.netsensia.rivalchess.model.helper.SliderDirection
import java.util.stream.Collectors.toList

object FenUtils {

    @kotlin.jvm.JvmStatic
    fun getBoardModel(fen: String): Board {
        val fenParts = fen.trim().split(" ").toTypedArray()
        if (fenParts.size < 4) {
            throw IllegalFenException(
                    "Expected at least 2 sections to FEN - board and mover"
            )
        }
        val boardBuilder: BoardBuilder = Board.builder()
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
        when (val mover = boardPart.toCharArray()[0]) {
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

    private fun collectRankParts(
            boardBuilder: BoardBuilder, rankParts: Array<String>, file: Int, rank: Int) {
        if (file == 8) {
            return
        }
        when (val piece = rankParts[rank].toCharArray()[file]) {
            'p' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BP)
            'P' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WP)
            'q' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BQ)
            'Q' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WQ)
            'r' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BR)
            'R' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WR)
            'b' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BB)
            'B' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WB)
            'n' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BN)
            'N' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WN)
            'k' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BK)
            'K' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WK)
            '-' -> boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.NONE)
            else -> throw IllegalFenException("Unexpected character in FEN: $piece")
        }
        collectRankParts(boardBuilder, rankParts, file + 1, rank)
    }

    @kotlin.jvm.JvmStatic
    fun invertFen(fen: String): String {
        var invertedFen = fen
        invertedFen = invertedFen.trim { it <= ' ' }
        invertedFen = invertedFen.replace(" b ", " . ")
        invertedFen = invertedFen.replace(" w ", " ; ")
        invertedFen = invertedFen.replace('Q', 'z')
        invertedFen = invertedFen.replace('K', 'x')
        invertedFen = invertedFen.replace('N', 'c')
        invertedFen = invertedFen.replace('B', 'v')
        invertedFen = invertedFen.replace('R', 'm')
        invertedFen = invertedFen.replace('P', ',')
        invertedFen = invertedFen.replace('q', 'Q')
        invertedFen = invertedFen.replace('k', 'K')
        invertedFen = invertedFen.replace('n', 'N')
        invertedFen = invertedFen.replace('b', 'B')
        invertedFen = invertedFen.replace('r', 'R')
        invertedFen = invertedFen.replace('p', 'P')
        invertedFen = invertedFen.replace('z', 'q')
        invertedFen = invertedFen.replace('x', 'k')
        invertedFen = invertedFen.replace('c', 'n')
        invertedFen = invertedFen.replace('v', 'b')
        invertedFen = invertedFen.replace('m', 'r')
        invertedFen = invertedFen.replace(',', 'p')
        invertedFen = invertedFen.replace(" . ", " w ")
        invertedFen = invertedFen.replace(" ; ", " b ")
        val fenParts = invertedFen.split(" ").toTypedArray()
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

    private fun getEmptyCount(board: Board, square: Square): Int {

        if (board.getSquareOccupant(square) != SquareOccupant.NONE) {
            return 0;
        }

        return 1 + if (square.isValidDirection(SliderDirection.E))
            getEmptyCount(board, square.fromDirection(SliderDirection.E))
        else 0
    }

    private fun getRowString(board: Board, square: Square): String {

        val sb = StringBuilder()

        val squareOccupant = board.getSquareOccupant(square)

        if (squareOccupant != SquareOccupant.NONE) {
            sb.append(squareOccupant.toChar())
            if (square.isValidDirection(SliderDirection.E)) {
                sb.append(getRowString(board, square.fromDirection(SliderDirection.E)))
            }
        } else {
            val emptyToRight = if (square.isValidDirection(SliderDirection.E))
                getEmptyCount(board, square.fromDirection(SliderDirection.E))
            else 0
            sb.append(1 + emptyToRight)
            val newSquare = square.fromDirection(SliderDirection.E, emptyToRight)
            if (newSquare.isValidDirection(SliderDirection.E)) {
                sb.append(getRowString(board, newSquare.fromDirection(SliderDirection.E)))
            }
        }

        return sb.toString()
    }

    @kotlin.jvm.JvmStatic
    fun Board.getFen(): String {
        val sb = StringBuilder()
        for (square in listOf(Square.A8, Square.A7, Square.A6, Square.A5, Square.A4, Square.A3, Square.A2)) {
            sb.append(getRowString(this, square));
            sb.append("/")
        }
        sb.append(getRowString(this, Square.A1));
        sb.append(" ");
        sb.append(this.sideToMove.value)
        sb.append(" ")
        sb.append(if (this.isKingSideCastleAvailable(Colour.BLACK)) "k" else "")
        sb.append(if (this.isQueenSideCastleAvailable(Colour.BLACK)) "q" else "")
        sb.append(if (this.isKingSideCastleAvailable(Colour.WHITE)) "K" else "")
        sb.append(if (this.isQueenSideCastleAvailable(Colour.WHITE)) "Q" else "")
        sb.append(" ")
        sb.append((this.enPassantFile + 97).toChar())
        sb.append(if (this.sideToMove == Colour.WHITE) '6' else '3')
        sb.append(" ")
        sb.append(this.halfMoveCount)
        sb.append(" ")
        sb.append(this.fullMoveCount)

        return sb.toString()

    }
}
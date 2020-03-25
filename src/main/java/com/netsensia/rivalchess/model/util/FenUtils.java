package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.exception.IllegalFenException;
import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.SquareOccupant;

public class FenUtils {

    private static final String FEN_START_POS = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    private FenUtils() {}

    public static String getStartPos() {
        return FEN_START_POS;
    }

    public static Board getBoardModel(final String fen) {

        final String[] fenParts = fen.split(" ");

        if (fenParts.length < 4) {
            throw new IllegalFenException(
                    "Expected at least 2 sections to FEN - board and mover"
            );
        }

        final Board.BoardBuilder boardBuilder = Board.builder();

        setBoardParts(boardBuilder, fenParts[0]);
        setMover(boardBuilder, fenParts[1]);
        setCastleFlags(boardBuilder, fenParts.length > 2 ? fenParts[2] : "kqKQ");
        setEnPassant(boardBuilder, fenParts.length > 3 ? fenParts[3] : "-");
        setHalfMoves(boardBuilder, fenParts.length > 4 ? fenParts[4] : "0");
        setFullMoves(boardBuilder, fenParts.length > 5 ? fenParts[5] : "0");

        return boardBuilder.build();

    }

    private static void setMover(final Board.BoardBuilder boardBuilder, final String boardPart) {
        if (boardPart.length() != 1) {
            throw new IllegalFenException("Unexpected error processing side to move");
        }

        final char mover = boardPart.toCharArray()[0];
        switch (mover) {
            case 'w': boardBuilder.withSideToMove(Colour.WHITE); break;
            case 'b': boardBuilder.withSideToMove(Colour.BLACK); break;
            default:
                throw new IllegalFenException("Invalid side to move: " + mover);
        }
    }

    private static void setEnPassant(final Board.BoardBuilder boardBuilder, final String boardPart) {
        if (boardPart.equals("-")) {
            boardBuilder.withEnPassantFile(-1);
            return;
        }
        if (boardPart.length() != 2) {
            throw new IllegalFenException("Unexpected error processing en passant part: " + boardPart);
        }

        final char enPassantFileAlgebraic = boardPart.toCharArray()[0];
        if (enPassantFileAlgebraic < 'a' || enPassantFileAlgebraic > 'h') {
            throw new IllegalFenException("Invalid en passant part: " + boardPart);
        }

        boardBuilder.withEnPassantFile(enPassantFileAlgebraic - 'a');
    }

    private static void setCastleFlags(final Board.BoardBuilder boardBuilder, final String boardPart) {
        boardBuilder.withIsWhiteQueenSideCastleAvailable(boardPart.contains("Q"));
        boardBuilder.withIsWhiteKingSideCastleAvailable(boardPart.contains("K"));
        boardBuilder.withIsBlackQueenSideCastleAvailable(boardPart.contains("q"));
        boardBuilder.withIsBlackKingSideCastleAvailable(boardPart.contains("k"));
    }

    private static void setHalfMoves(final Board.BoardBuilder boardBuilder, final String boardPart) {
        boardBuilder.withHalfMoveCount(Integer.parseInt(boardPart));
    }

    private static void setFullMoves(final Board.BoardBuilder boardBuilder, final String boardPart) {
        boardBuilder.withFullMoveCount(Integer.parseInt(boardPart));
    }

    private static void setBoardParts(final Board.BoardBuilder boardBuilder, final String boardPart) {

        final String[] rankParts = boardPart.split("/");

        if (rankParts.length != 8) {
            throw new IllegalFenException(
                    "Expected 8 ranks in board part of FEN - found " + rankParts.length
            );
        }

        for (int i=0; i<8; i++) {
            rankParts[i] = expand(rankParts[i]);
        }

        getBoardFromRank(boardBuilder, rankParts, 0);
    }

    private static String expand(String rankPart) {
        return rankPart
                .replace("1", "-")
                .replace("2", "--")
                .replace("3", "---")
                .replace("4", "----")
                .replace("5", "-----")
                .replace("6", "------")
                .replace("7", "-------")
                .replace("8", "--------");
    }

    private static void getBoardFromRank(
            final Board.BoardBuilder boardBuilder, final String[] rankParts, final int rank) {

        if (rank < 8) {
            collectRankParts(boardBuilder, rankParts, 0, rank);
            getBoardFromRank(boardBuilder, rankParts, rank+1);
        }

    }

    public static void collectRankParts(
            final Board.BoardBuilder boardBuilder, final String[] rankParts, final int file, final int rank) {

        if (file == 8) {
            return;
        }

        final char piece = rankParts[rank].toCharArray()[file];
        switch (piece) {
            case 'p' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BP); break;
            case 'P' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WP); break;
            case 'q' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BQ); break;
            case 'Q' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WQ); break;
            case 'r' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BR); break;
            case 'R' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WR); break;
            case 'b' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BB); break;
            case 'B' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WB); break;
            case 'n' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BN); break;
            case 'N' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WN); break;
            case 'k' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.BK); break;
            case 'K' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.WK); break;
            case '-' : boardBuilder.withSquareOccupant(Square.fromCoords(file, rank), SquareOccupant.NONE); break;
            default:
                throw new IllegalFenException("Unexpected character in FEN: " + piece);
        }
        collectRankParts(boardBuilder, rankParts, file+1, rank);
    }

    public static String invertFen(String fen) {
        fen = fen.trim();

        fen = fen.replace(" b ", " . ");
        fen = fen.replace(" w ", " ; ");

        fen = fen.replace('Q', 'z');
        fen = fen.replace('K', 'x');
        fen = fen.replace('N', 'c');
        fen = fen.replace('B', 'v');
        fen = fen.replace('R', 'm');
        fen = fen.replace('P', ',');

        fen = fen.replace('q', 'Q');
        fen = fen.replace('k', 'K');
        fen = fen.replace('n', 'N');
        fen = fen.replace('b', 'B');
        fen = fen.replace('r', 'R');
        fen = fen.replace('p', 'P');

        fen = fen.replace('z', 'q');
        fen = fen.replace('x', 'k');
        fen = fen.replace('c', 'n');
        fen = fen.replace('v', 'b');
        fen = fen.replace('m', 'r');
        fen = fen.replace(',', 'p');

        fen = fen.replace(" . ", " w ");
        fen = fen.replace(" ; ", " b ");

        String[] fenParts = fen.split(" ");
        String[] boardParts = fenParts[0].split("/");

        String newFen =
                boardParts[7] + "/" +
                        boardParts[6] + "/" +
                        boardParts[5] + "/" +
                        boardParts[4] + "/" +
                        boardParts[3] + "/" +
                        boardParts[2] + "/" +
                        boardParts[1] + "/" +
                        boardParts[0];

        StringBuilder newFenBuilder = new StringBuilder(newFen);

        for (int i = 1; i < fenParts.length; i++) {
            if (i == 3) {
                newFenBuilder.append(" ").append(invertSquare(fenParts[i]));
            } else {
                newFenBuilder.append(" ").append(fenParts[i]);
            }
        }

        return newFenBuilder.toString();
    }

    private static String invertSquare(final String square) {
        if (square.equals("-")) {
            return square;
        }

        if (square.length() != 2) {
            throw new IllegalFenException("Invalid square reference " + square);
        }

        final char file = square.charAt(0);
        final char rank = square.charAt(1);

        final char newFile = (char)('h' - file + 'a');
        final char newRank = (char)('8' - rank + '1');

        return String.valueOf(newFile) + newRank;
    }
}

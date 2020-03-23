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

    public static Board getBoardModel(final String fenStr) {

        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder();

        if (fenStr.trim().equals("")) {
            throw new IllegalFenException("Empty FEN");
        }

        int fenIndex = 0;
        int boardArrayIndex = 0;

        for (int i = 0; i < fenStr.length(); i++) {
            final char fenToken = fenStr.charAt(fenIndex++);

            if (fenToken >= '0' && fenToken <= '9') {
                boardArrayIndex = padBoardWithSpaces(boardBuilder, boardArrayIndex, fenToken);
            } else if (fenToken != '/') {
                boardArrayIndex = setPiece(boardBuilder, boardArrayIndex, fenToken);
            }

            if (fenToken == ' ') {
                throw new IllegalFenException("Unexpected space character found");
            }

            if (boardArrayIndex == 64) {
                break;
            }

            if (boardArrayIndex > 64) {
                throw new IllegalFenException("Invalid boardArrayIndex");
            }
        }

        fenIndex++;

        final char fenToken = fenStr.charAt(fenIndex++);

        verifyMoverChar(fenToken);

        boardBuilder.withSideToMove(fenToken == 'w' ? Colour.WHITE : Colour.BLACK);

        fenIndex++;

        final String castleFlags = fenStr.substring(fenIndex, fenStr.indexOf(' ', fenIndex));

        boardBuilder.withIsWhiteQueenSideCastleAvailable(castleFlags.contains("Q"));
        boardBuilder.withIsWhiteKingSideCastleAvailable(castleFlags.contains("K"));
        boardBuilder.withIsBlackQueenSideCastleAvailable(castleFlags.contains("q"));
        boardBuilder.withIsBlackKingSideCastleAvailable(castleFlags.contains("k"));

        final char enPassantChar = fenStr.charAt(fenIndex + castleFlags.length() + 1);

        boardBuilder.withEnPassantFile(enPassantChar != '-' ? enPassantChar - 97 : -1);

        return boardBuilder.build();
    }

    private static void verifyMoverChar(char fenToken) {
        if (fenToken != 'w' && fenToken != 'b') {
            throw new IllegalFenException("Illegal mover in FEN");
        }
    }

    private static int setPiece(Board.BoardBuilder boardBuilder, int boardArrayIndex, char fenToken) {
        final int targetXFile = boardArrayIndex % 8;
        final int targetYRank = boardArrayIndex / 8;

        boardBuilder.withSquareOccupant(Square.fromCoords(targetXFile, targetYRank), SquareOccupant.fromChar(fenToken));

        return boardArrayIndex+1;
    }

    private static int padBoardWithSpaces(
            final  Board.BoardBuilder boardBuilder,
            int boardArrayIndex,
            final char fenToken
    ) {
        for (int n = 1; n <= Character.digit(fenToken, 10); n++) {
            boardArrayIndex = setPiece(boardBuilder, boardArrayIndex, '_');
        }
        return boardArrayIndex;
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

package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.util.BoardUtils;
import com.netsensia.rivalchess.model.util.FenUtils;
import com.netsensia.rivalchess.model.util.MoveMaker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Board {

	private static final int NUM_FILES = 8;
    private static final int NUM_RANKS = 8;

    private final Map<Square, SquareOccupant> squareOccupants;

    private int enPassantFile = -1;

    private boolean isWhiteKingSideCastleAvailable = true;
    private boolean isWhiteQueenSideCastleAvailable = true;
    private boolean isBlackKingSideCastleAvailable = true;
    private boolean isBlackQueenSideCastleAvailable = true;

    private int halfMoveCount = 0;
    private Colour sideToMove;

    public Board() {
        squareOccupants = new HashMap<>();
        for (int x=0; x<8; x++) {
            for (int y=0; y<8; y++) {
                squareOccupants.put(
                        new Square(x,y),
                        SquareOccupant.NONE
                );
            }
        }
    }

    public static Board fromFen(String fen) {
        return FenUtils.getBoardModel(fen);
    }

    public static Board copy(final Board board) {
        Board newBoard = new Board();

        for (int x=0; x<8; x++) {
            for (int y=0; y<8; y++) {
                newBoard.setSquareOccupant(x, y, board.getSquareOccupant(x,y));
            }
        }

        newBoard.setSideToMove(board.getSideToMove());
        newBoard.setEnPassantFile(board.getEnPassantFile());

        newBoard.setKingSideCastleAvailable(Colour.WHITE, board.isKingSideCastleAvailable(Colour.WHITE));
        newBoard.setKingSideCastleAvailable(Colour.BLACK, board.isKingSideCastleAvailable(Colour.BLACK));
        newBoard.setQueenSideCastleAvailable(Colour.WHITE, board.isQueenSideCastleAvailable(Colour.WHITE));
        newBoard.setQueenSideCastleAvailable(Colour.BLACK, board.isQueenSideCastleAvailable(Colour.BLACK));

        newBoard.setHalfMoveCount(board.getHalfMoveCount());

        return newBoard;
    }

    public static Board fromMove(final Board board, final Move move) {
        return MoveMaker.makeMove(board, move);
    }

    public SquareOccupant getSquareOccupant(Square boardRef) {
        return squareOccupants.get(boardRef);
    }

    public SquareOccupant getSquareOccupant(int xFile, int yRank) {
        return getSquareOccupant(new Square(xFile, yRank));
    }

    public void setSquareOccupant(Square boardRef, SquareOccupant squareOccupant) {
        squareOccupants.put(boardRef, squareOccupant);
    }

    public void setSquareOccupant(int xFile, int yRank, SquareOccupant squareOccupant) {
        setSquareOccupant(new Square(xFile, yRank), squareOccupant);
    }

    private int getBoardArrayIndex(Square square) {
        return getBoardArrayIndex(square.getXFile(), square.getYRank());
    }

    private int getBoardArrayIndex(int xFile, int yRank) {
        return (NUM_FILES * yRank) + xFile;
    }

    public int getNumXFiles() {
        return NUM_FILES;
    }

    public int getNumYRanks() {
        return NUM_RANKS;
    }

    public boolean isKingSideCastleAvailable(final Colour colour) {
        return colour == Colour.WHITE ? isWhiteKingSideCastleAvailable : isBlackKingSideCastleAvailable;
    }

    public void setKingSideCastleAvailable(final Colour colour, final boolean isKingSideCastleAvailable) {
        if (colour == Colour.WHITE) {
            isWhiteKingSideCastleAvailable = isKingSideCastleAvailable;
        } else {
            isBlackKingSideCastleAvailable = isKingSideCastleAvailable;
        }
    }

    public boolean isQueenSideCastleAvailable(final Colour colour) {
        return colour == Colour.WHITE ? isWhiteQueenSideCastleAvailable : isBlackQueenSideCastleAvailable;
    }

    public void setQueenSideCastleAvailable(final Colour colour, final boolean isQueenSideCastleAvailable) {
        if (colour == Colour.WHITE) {
            this.isWhiteQueenSideCastleAvailable = isQueenSideCastleAvailable;
        } else {
            this.isBlackQueenSideCastleAvailable = isQueenSideCastleAvailable;
        }
    }

    public Stream<Map.Entry<Square, SquareOccupant>> squareOccupantStream() {
        return squareOccupants.entrySet().stream();
    }

    public void setHalfMoveCount(int halfMoveCount) {
        this.halfMoveCount = halfMoveCount;
    }

    public int getHalfMoveCount() {
        return this.halfMoveCount;
    }

    public Colour getSideToMove() {
        return sideToMove;
    }

    public void setSideToMove(final Colour sideToMove) {
        this.sideToMove = sideToMove;
    }

    public int getEnPassantFile() {
        return this.enPassantFile;
    }

    public void setEnPassantFile(final int enPassantFile) {
        this.enPassantFile = enPassantFile;
    }

    public List<Move> getLegalMoves() {
        return BoardUtils.getLegalMoves(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Board) {
            Board bo = (Board) o;

            for (int x=0; x<8; x++) {
                for (int y=0; y<8; y++) {
                    if (!this.getSquareOccupant(x,y).equals(bo.getSquareOccupant(x,y))) {
                        return false;
                    }
                }
            }

            return this.getHalfMoveCount() == bo.getHalfMoveCount() &&
                    this.getEnPassantFile() == bo.getEnPassantFile() &&
                    this.getSideToMove() == bo.getSideToMove() &&
                    this.isKingSideCastleAvailable(Colour.WHITE) == bo.isKingSideCastleAvailable(Colour.WHITE) &&
                    this.isKingSideCastleAvailable(Colour.BLACK) == bo.isKingSideCastleAvailable(Colour.BLACK) &&
                    this.isQueenSideCastleAvailable(Colour.WHITE) == bo.isQueenSideCastleAvailable(Colour.WHITE) &&
                    this.isQueenSideCastleAvailable(Colour.BLACK) == bo.isQueenSideCastleAvailable(Colour.BLACK);

        }

        return false;
    }

}

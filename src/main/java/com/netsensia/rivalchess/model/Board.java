package com.netsensia.rivalchess.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Board {

	private static final int NUM_FILES = 8;
    private static final int NUM_RANKS = 8;

    private final Map<Square, SquareOccupant> squareOccupants;

    private int enPassantFile = 0;

    private boolean isWhiteKingSideCastleAvailable = true;
    private boolean isWhiteQueenSideCastleAvailable = true;
    private boolean isBlackKingSideCastleAvailable = true;
    private boolean isBlackQueenSideCastleAvailable = true;

    private final int halfMoveCount = 0;
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

}

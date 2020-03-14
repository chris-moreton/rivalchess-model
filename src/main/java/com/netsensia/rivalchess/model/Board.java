package com.netsensia.rivalchess.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean isWhiteKingSideCastleAvailable() {
        return isWhiteKingSideCastleAvailable;
    }

    public void setWhiteKingSideCastleAvailable(final boolean isWhiteKingSideCastleAvailable) {
        this.isWhiteKingSideCastleAvailable = isWhiteKingSideCastleAvailable;
    }

    public boolean isWhiteQueenSideCastleAvailable() {
        return isWhiteQueenSideCastleAvailable;
    }

    public void setWhiteQueenSideCastleAvailable(final boolean isWhiteQueenSideCastleAvailable) {
        this.isWhiteQueenSideCastleAvailable = isWhiteQueenSideCastleAvailable;
    }

    public boolean isBlackKingSideCastleAvailable() {
        return this.isBlackKingSideCastleAvailable;
    }

    public void setBlackKingSideCastleAvailable(final boolean isBlackKingSideCastleAvailable) {
        this.isBlackKingSideCastleAvailable = isBlackKingSideCastleAvailable;
    }

    public boolean isBlackQueenSideCastleAvailable() {
        return this.isBlackQueenSideCastleAvailable;
    }

    public void setBlackQueenSideCastleAvailable(final boolean isBlackQueenSideCastleAvailable) {
        this.isBlackQueenSideCastleAvailable = isBlackQueenSideCastleAvailable;
    }

    public List<Square> getSquaresWithOccupant(SquareOccupant squareOccupant) {
        return null;
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

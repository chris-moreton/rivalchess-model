package com.netsensia.rivalchess.model;

public class Board {

	private static final int NUM_FILES = 8;
    private static final int NUM_RANKS = 8;

    private final char[] boardArray;
    private int enPassantFile = 0;

    private boolean isWhiteKingSideCastleAvailable = true;
    private boolean isWhiteQueenSideCastleAvailable = true;
    private boolean isBlackKingSideCastleAvailable = true;
    private boolean isBlackQueenSideCastleAvailable = true;

    private final int halfMoveCount = 0;
    private Colour sideToMove;

    public Board() {
        this.boardArray = new char[NUM_FILES * NUM_RANKS];
    }

    public SquareOccupant getSquareOccupant(Square boardRef) {
        return this.getSquareOccupant(boardRef.getXFile(), boardRef.getYRank());
    }

    public SquareOccupant getSquareOccupant(int xFile, int yRank) {
        return SquareOccupant.fromChar(boardArray[this.getBoardArrayIndex(xFile, yRank)]);
    }

    public void setSquareOccupant(Square boardRef, SquareOccupant squareOccupant) {
        boardArray[getBoardArrayIndex(boardRef.getXFile(), boardRef.getYRank())]
                = squareOccupant.toChar();
    }

    public void setSquareOccupant(int xFile, int yRank, SquareOccupant squareOccupant) {
        boardArray[getBoardArrayIndex(xFile, yRank)] = squareOccupant.toChar();
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

package com.netsensia.rivalchess.model;

public class Board {

	private static final int DEFAULT_BOARD_NUM_FILES = 8;
    private static final int DEFAULT_BOARD_NUM_RANKS = 8;

    public static final char VACANT_TILE = '_';

    private boolean isWhiteKingSideCastleAvailable = true;
    private boolean isWhiteQueenSideCastleAvailable = true;
    private boolean isBlackKingSideCastleAvailable = true;
    private boolean isBlackQueenSideCastleAvailable = true;

    private final int halfMoveCount = 0;
    private boolean isWhiteToMove = true;

    public Board() {
        this(DEFAULT_BOARD_NUM_FILES, DEFAULT_BOARD_NUM_RANKS);
    }

    public Board(int numXFiles, int numYRanks) {
        this.numXFiles = numXFiles;
        this.numYRanks = numYRanks;
        this.boardArray = new char[this.numYRanks * this.numXFiles];
    }

    public char getPieceCode(Square boardRef) {
        return this.getPieceCode(boardRef.getXFile(), boardRef.getYRank());
    }

    public char getPieceCode(int xFile, int yRank) {
        return this.boardArray[this.getBoardArrayIndex(xFile, yRank)];
    }

    public void setPieceCode(int xFile, int yRank, char pieceCode) {
        this.boardArray[this.getBoardArrayIndex(xFile, yRank)] = pieceCode;
    }

    protected int getBoardArrayIndex(int xFile, int yRank) {
        return (this.numXFiles * yRank) + xFile;
    }

    public int getNumXFiles() {
        return this.numXFiles;
    }

    public int getNumYRanks() {
        return this.numYRanks;
    }

    public boolean isWhiteKingSideCastleAvailable() {
        return this.isWhiteKingSideCastleAvailable;
    }

    public void setWhiteKingSideCastleAvailable(boolean isWhiteKingSideCastleAvailable) {
        this.isWhiteKingSideCastleAvailable = isWhiteKingSideCastleAvailable;
    }

    public boolean isWhiteQueenSideCastleAvailable() {
        return this.isWhiteQueenSideCastleAvailable;
    }

    public void setWhiteQueenSideCastleAvailable(boolean isWhiteQueenSideCastleAvailable) {
        this.isWhiteQueenSideCastleAvailable = isWhiteQueenSideCastleAvailable;
    }

    public boolean isBlackKingSideCastleAvailable() {
        return this.isBlackKingSideCastleAvailable;
    }

    public void setBlackKingSideCastleAvailable(boolean isBlackKingSideCastleAvailable) {
        this.isBlackKingSideCastleAvailable = isBlackKingSideCastleAvailable;
    }

    public boolean isBlackQueenSideCastleAvailable() {
        return this.isBlackQueenSideCastleAvailable;
    }

    public void setBlackQueenSideCastleAvailable(boolean isBlackQueenSideCastleAvailable) {
        this.isBlackQueenSideCastleAvailable = isBlackQueenSideCastleAvailable;
    }

    public int getHalfMoveCount() {
        return this.halfMoveCount;
    }

    public boolean isWhiteToMove() {
        return this.isWhiteToMove;
    }

    public boolean isBlackToMove() {
        return !this.isWhiteToMove;
    }

    public void setWhiteToMove(boolean isWhiteToMove) {
        this.isWhiteToMove = isWhiteToMove;
    }

    public int getEnPassantFile() {
        return this.enPassantFile;
    }

    public void setEnPassantFile(int enPassantFile) {
        this.enPassantFile = enPassantFile;
    }
}

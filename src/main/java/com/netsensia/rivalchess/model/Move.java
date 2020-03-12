package com.netsensia.rivalchess.model;

public class Move {
    private int srcXFile;
    private int srcYRank;

    private int tgtXFile;
    private int tgtYRank;

    final private int numFiles;

    final static private int DEFAULT_FILE_COUNT = 8;

    private SquareOccupant promotedPiece = SquareOccupant.NONE;

    public Move(Square srcBoardRef, Square tgtBoardRef) {
        this(srcBoardRef.getXFile(), srcBoardRef.getYRank(), tgtBoardRef.getXFile(), tgtBoardRef.getYRank());
    }

    public Move(int srcXFile, int srcYRank, int tgtXFile, int tgtYRank) {
        this(srcXFile, srcYRank, tgtXFile, tgtYRank, DEFAULT_FILE_COUNT);
    }

    public Move(int srcXFile, int srcYRank, int tgtXFile, int tgtYRank, int numFiles) {
        this.numFiles = numFiles;
        this.set(srcXFile, srcYRank, tgtXFile, tgtYRank);
    }

    public Square getSrcBoardRef() {
        return new Square(this.getSrcXFile(), this.getSrcYRank());
    }

    public Square getTgtBoardRef() {
        return new Square(this.getTgtXFile(), this.getTgtYRank());
    }

    public int getSrcXFile() {
        return this.srcXFile;
    }

    public int getSrcYRank() {
        return this.srcYRank;
    }

    public int getTgtXFile() {
        return this.tgtXFile;
    }

    public int getTgtYRank() {
        return this.tgtYRank;
    }

    public void set(int srcXFile, int srcYRank, int tgtXFile, int tgtYRank) {
        this.srcXFile = srcXFile;
        this.srcYRank = srcYRank;

        this.tgtXFile = tgtXFile;
        this.tgtYRank = tgtYRank;
    }

    public void setPromotedPiece(SquareOccupant promotedPieceCode) {
        this.promotedPiece = promotedPieceCode;
    }

    public SquareOccupant getPromotedPiece() {
        return this.promotedPiece;
    }

    @Override
    public String toString() {
        return
                this.getSrcBoardRef().getAlgebraic(numFiles) +
                        this.getTgtBoardRef().getAlgebraic(numFiles) +
                        (promotedPiece == SquareOccupant.NONE
                                ? ""
                                : promotedPiece.toChar());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Move) {
            Move move = (Move) o;
            return (move.getSrcBoardRef().equals(this.getSrcBoardRef())
                    && move.getTgtBoardRef().equals(this.getTgtBoardRef())
                    && move.getPromotedPiece() == this.getPromotedPiece());
            }
        return false;
    }

}

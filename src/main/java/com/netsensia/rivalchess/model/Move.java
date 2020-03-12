package com.netsensia.rivalchess.model;

public class Move {
    final private int srcXFile;
    final private int srcYRank;

    final private int tgtXFile;
    final private int tgtYRank;

    final private int numFiles;

    final static private int DEFAULT_FILE_COUNT = 8;

    final private SquareOccupant promotedPiece;

    public Move(
            final Square srcBoardRef,
            final Square tgtBoardRef
    ) {
        this(
                srcBoardRef.getXFile(),
                srcBoardRef.getYRank(),
                tgtBoardRef.getXFile(),
                tgtBoardRef.getYRank(),
                SquareOccupant.NONE);
    }

    public Move(
            final Square srcBoardRef,
            final Square tgtBoardRef,
            final SquareOccupant promotionPiece
    ) {
        this(
                srcBoardRef.getXFile(),
                srcBoardRef.getYRank(),
                tgtBoardRef.getXFile(),
                tgtBoardRef.getYRank(),
                promotionPiece);
    }

    public Move(
            final int srcXFile,
            final int srcYRank,
            final int tgtXFile,
            final int tgtYRank
    ) {
        this(srcXFile, srcYRank, tgtXFile, tgtYRank, SquareOccupant.NONE, DEFAULT_FILE_COUNT);
    }

    public Move(
            final int srcXFile,
            final int srcYRank,
            final int tgtXFile,
            final int tgtYRank,
            final SquareOccupant promotionPiece
    ) {
        this(srcXFile, srcYRank, tgtXFile, tgtYRank, promotionPiece, DEFAULT_FILE_COUNT);
    }

    public Move(
            final int srcXFile,
            final int srcYRank,
            final int tgtXFile,
            final int tgtYRank,
            final SquareOccupant promotionPiece,
            final int numFiles
    ) {
        this.numFiles = numFiles;
        this.srcXFile = srcXFile;
        this.srcYRank = srcYRank;
        this.tgtXFile = tgtXFile;
        this.tgtYRank = tgtYRank;
        this.promotedPiece = promotionPiece;
    }

    public static Move fromAlgebraic(String algebraic) {

        if (algebraic.length() < 4 || algebraic.length() > 5) {
            throw new RuntimeException("Algebraic move must be four or five characters");
        }

        final Square from = Square.fromAlgebraic(algebraic.substring(0, 2));
        final Square to = Square.fromAlgebraic(algebraic.substring(2, 4));

        final SquareOccupant promotionPiece =
                algebraic.length() == 5
                ? SquareOccupant.fromChar(algebraic.toCharArray()[4])
                : SquareOccupant.NONE;

        return new Move(from, to, promotionPiece);
    }

    public Square getSrcBoardRef() {
        return new Square(srcXFile, srcYRank);
    }

    public Square getTgtBoardRef() {
        return new Square(tgtXFile, tgtYRank);
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

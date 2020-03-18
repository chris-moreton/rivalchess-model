package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.exception.InvalidSimpleAlgebraicMoveException;

public class Move implements Comparable {
    private final int srcXFile;
    private final int srcYRank;

    private final int tgtXFile;
    private final int tgtYRank;

    private final SquareOccupant promotedPiece;

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

    @Deprecated
    public Move(
            final int srcXFile,
            final int srcYRank,
            final int tgtXFile,
            final int tgtYRank
    ) {
        this(srcXFile, srcYRank, tgtXFile, tgtYRank, SquareOccupant.NONE);
    }

    @Deprecated
    public Move(
            final int srcXFile,
            final int srcYRank,
            final int tgtXFile,
            final int tgtYRank,
            final SquareOccupant promotionPiece
    ) {
        this.srcXFile = srcXFile;
        this.srcYRank = srcYRank;
        this.tgtXFile = tgtXFile;
        this.tgtYRank = tgtYRank;
        this.promotedPiece = promotionPiece;
    }

    public Move(String algebraic) {

        if (algebraic.length() < 4 || algebraic.length() > 5) {
            throw new InvalidSimpleAlgebraicMoveException("Algebraic move must be four or five characters");
        }

        final Square from = Square.fromAlgebraic(algebraic.substring(0, 2));
        final Square to = Square.fromAlgebraic(algebraic.substring(2, 4));

        final SquareOccupant promotionPiece =
                algebraic.length() == 5
                ? SquareOccupant.fromChar(algebraic.toCharArray()[4])
                : SquareOccupant.NONE;

        this.srcXFile = from.getXFile();
        this.srcYRank = from.getYRank();
        this.tgtXFile = to.getXFile();
        this.tgtYRank = to.getYRank();
        this.promotedPiece = promotionPiece;
    }

    public Square getSrcBoardRef() {
        return Square.fromCoords(srcXFile, srcYRank);
    }

    public Square getTgtBoardRef() {
        return Square.fromCoords(tgtXFile, tgtYRank);
    }

    public SquareOccupant getPromotedPiece() {
        return this.promotedPiece;
    }

    @Override
    public String toString() {
        return
                this.getSrcBoardRef().getAlgebraic() +
                        this.getTgtBoardRef().getAlgebraic() +
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

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

}

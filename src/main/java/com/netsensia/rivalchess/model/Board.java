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
        for (Square square : Square.values()) {
            squareOccupants.put(
                    square,
                    SquareOccupant.NONE
            );
        }
    }

    public static Board fromFen(String fen) {
        return FenUtils.getBoardModel(fen);
    }

    public Map<Square, SquareOccupant> getSquareOccupants() {
        return new HashMap<>(squareOccupants);
    }

    public Board(final Board board) {

        squareOccupants = board.getSquareOccupants();

        setSideToMove(board.getSideToMove());
        setEnPassantFile(board.getEnPassantFile());

        setKingSideCastleAvailable(Colour.WHITE, board.isKingSideCastleAvailable(Colour.WHITE));
        setKingSideCastleAvailable(Colour.BLACK, board.isKingSideCastleAvailable(Colour.BLACK));
        setQueenSideCastleAvailable(Colour.WHITE, board.isQueenSideCastleAvailable(Colour.WHITE));
        setQueenSideCastleAvailable(Colour.BLACK, board.isQueenSideCastleAvailable(Colour.BLACK));

        setHalfMoveCount(board.getHalfMoveCount());
    }

    public static Board fromMove(final Board board, final Move move) {
        return MoveMaker.makeMove(board, move);
    }

    public SquareOccupant getSquareOccupant(Square square) {
        return squareOccupants.get(square);
    }

    public void setSquareOccupant(Square square, SquareOccupant squareOccupant) {
        squareOccupants.put(square, squareOccupant);
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

            return this.squareOccupants.equals(((Board) o).getSquareOccupants()) &&
                    this.getEnPassantFile() == bo.getEnPassantFile() &&
                    this.getSideToMove() == bo.getSideToMove() &&
                    this.isKingSideCastleAvailable(Colour.WHITE) == bo.isKingSideCastleAvailable(Colour.WHITE) &&
                    this.isKingSideCastleAvailable(Colour.BLACK) == bo.isKingSideCastleAvailable(Colour.BLACK) &&
                    this.isQueenSideCastleAvailable(Colour.WHITE) == bo.isQueenSideCastleAvailable(Colour.WHITE) &&
                    this.isQueenSideCastleAvailable(Colour.BLACK) == bo.isQueenSideCastleAvailable(Colour.BLACK);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(squareOccupants);

        s.append("Castle privileges: " +
                isWhiteKingSideCastleAvailable +
                isWhiteQueenSideCastleAvailable +
                isBlackKingSideCastleAvailable +
                isBlackQueenSideCastleAvailable);
        s.append("\n");
        s.append("En passant file: " + enPassantFile);
        s.append("\n");
        s.append("Half move count: " + halfMoveCount);

        return s.toString();
    }

}

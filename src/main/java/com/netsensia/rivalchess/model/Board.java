package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.util.BoardUtils;
import com.netsensia.rivalchess.model.util.FenUtils;
import com.netsensia.rivalchess.model.util.MoveMaker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Board {

    private final Map<Square, SquareOccupant> squareOccupants;

    private final int enPassantFile;

    private final boolean isWhiteKingSideCastleAvailable;
    private final boolean isWhiteQueenSideCastleAvailable;
    private final boolean isBlackKingSideCastleAvailable;
    private final boolean isBlackQueenSideCastleAvailable;

    private final int halfMoveCount;
    private final Colour sideToMove;

    public Board(final BoardBuilder builder) {
        this.squareOccupants = new HashMap<>(builder.squareOccupants);
        this.enPassantFile = builder.enPassantFile;
        this.isWhiteQueenSideCastleAvailable = builder.isWhiteQueenSideCastleAvailable;
        this.isBlackQueenSideCastleAvailable = builder.isBlackQueenSideCastleAvailable;
        this.isWhiteKingSideCastleAvailable = builder.isWhiteKingSideCastleAvailable;
        this.isBlackKingSideCastleAvailable = builder.isBlackKingSideCastleAvailable;
        this.halfMoveCount = builder.halfMoveCount;
        this.sideToMove = builder.sideToMove;
    }

    public static Board fromFen(final String fen) {
        return FenUtils.getBoardModel(fen);
    }

    public Map<Square, SquareOccupant> getSquareOccupants() {
        return new HashMap<>(squareOccupants);
    }

    public Board(final Board board) {

        squareOccupants = board.getSquareOccupants();

        this.sideToMove = board.getSideToMove();
        this.enPassantFile = board.getEnPassantFile();

        this.isBlackKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.BLACK);
        this.isBlackQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.BLACK);
        this.isWhiteKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.WHITE);
        this.isWhiteQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.WHITE);

        this.halfMoveCount = board.getHalfMoveCount();
    }

    public static Board fromMove(final Board board, final Move move) {
        return MoveMaker.makeMove(board, move);
    }

    public SquareOccupant getSquareOccupant(Square square) {
        return squareOccupants.get(square);
    }

    public boolean isKingSideCastleAvailable(final Colour colour) {
        return colour == Colour.WHITE ? isWhiteKingSideCastleAvailable : isBlackKingSideCastleAvailable;
    }

    public boolean isQueenSideCastleAvailable(final Colour colour) {
        return colour == Colour.WHITE ? isWhiteQueenSideCastleAvailable : isBlackQueenSideCastleAvailable;
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

    public int getEnPassantFile() {
        return this.enPassantFile;
    }

    public List<Move> getLegalMoves() {
        return BoardUtils.getLegalMoves(this);
    }

    public boolean isCheck() {
        return BoardUtils.isCheck(this);
    }

    public List<Square> getSquaresWithOccupant(final SquareOccupant squareOccupant) {
        return BoardUtils.getSquaresWithOccupant(this, squareOccupant);
    }

    public boolean isSquareAttackedBy(final Square square, final Colour byColour) {
        return BoardUtils.isSquareAttackedBy(this, square, byColour);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Board) {
            Board bo = (Board) o;

            for (Square square : Square.values()) {
                if (getSquareOccupant(square) != bo.getSquareOccupant(square)) {
                    return false;
                }
            }

            return  this.getEnPassantFile() == bo.getEnPassantFile() &&
                    this.getSideToMove() == bo.getSideToMove() &&
                    this.getHalfMoveCount() == bo.getHalfMoveCount() &&
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

    public static class BoardBuilder
    {
        private Map<Square, SquareOccupant> squareOccupants;

        private int enPassantFile;

        private boolean isWhiteKingSideCastleAvailable;
        private boolean isWhiteQueenSideCastleAvailable;
        private boolean isBlackKingSideCastleAvailable;
        private boolean isBlackQueenSideCastleAvailable;

        private int halfMoveCount;
        private Colour sideToMove;

        public BoardBuilder() {
            enPassantFile = -1;
            isBlackQueenSideCastleAvailable = true;
            isWhiteQueenSideCastleAvailable = true;
            isWhiteKingSideCastleAvailable = true;
            isBlackKingSideCastleAvailable = true;
            halfMoveCount = 0;
            sideToMove = Colour.WHITE;

            squareOccupants = new HashMap<>();
            for (Square square : Square.values()) {
                squareOccupants.put(
                        square,
                        SquareOccupant.NONE
                );
            }
        }

        public BoardBuilder(final Board board) {
            this.squareOccupants = new HashMap<>(board.getSquareOccupants());
            this.enPassantFile = board.getEnPassantFile();
            this.isWhiteKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.WHITE);
            this.isBlackKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.BLACK);
            this.isWhiteQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.WHITE);
            this.isBlackQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.BLACK);
            this.halfMoveCount = board.getHalfMoveCount();
            this.sideToMove = board.getSideToMove();
        }

        public BoardBuilder withSquareOccupants(final Map<Square, SquareOccupant> squareOccupants) {
            this.squareOccupants = new HashMap<>(squareOccupants);
            return this;
        }

        public BoardBuilder withSquareOccupant(final Square square, final SquareOccupant squareOccupant) {
            this.squareOccupants.put(square, squareOccupant);
            return this;
        }

        public BoardBuilder withEnPassantFile(final int enPassantFile) {
            this.enPassantFile = enPassantFile;
            return this;
        }

        public BoardBuilder withHalfMoveCount(final int halfMoveCount) {
            this.halfMoveCount = halfMoveCount;
            return this;
        }

        public BoardBuilder withSideToMove(final Colour sideToMove) {
            this.sideToMove = sideToMove;
            return this;
        }

        public BoardBuilder withIsWhiteKingSideCastleAvailable(final boolean isWhiteKingSideCastleAvailable) {
            this.isWhiteKingSideCastleAvailable = isWhiteKingSideCastleAvailable;
            return this;
        }

        public BoardBuilder withIsBlackKingSideCastleAvailable(final boolean isBlackKingSideCastleAvailable) {
            this.isBlackKingSideCastleAvailable = isBlackKingSideCastleAvailable;
            return this;
        }

        public BoardBuilder withIsWhiteQueenSideCastleAvailable(final boolean isWhiteQueenSideCastleAvailable) {
            this.isWhiteQueenSideCastleAvailable = isWhiteQueenSideCastleAvailable;
            return this;
        }

        public BoardBuilder withIsBlackQueenSideCastleAvailable(final boolean isBlackQueenSideCastleAvailable) {
            this.isBlackQueenSideCastleAvailable = isBlackQueenSideCastleAvailable;
            return this;
        }

        public BoardBuilder withIsQueenSideCastleAvailable(final Colour colour, final boolean isQueenSideCastleAvailable) {
            if (colour == Colour.WHITE) {
                this.isWhiteQueenSideCastleAvailable = isQueenSideCastleAvailable;
            } else {
                this.isBlackQueenSideCastleAvailable = isQueenSideCastleAvailable;
            }
            return this;
        }

        public BoardBuilder withIsKingSideCastleAvailable(final Colour colour, final boolean isKingSideCastleAvailable) {
            if (colour == Colour.WHITE) {
                this.isWhiteKingSideCastleAvailable = isKingSideCastleAvailable;
            } else {
                this.isBlackKingSideCastleAvailable = isKingSideCastleAvailable;
            }
            return this;
        }

        public Board build() {
            return new Board(this);
        }

    }
}

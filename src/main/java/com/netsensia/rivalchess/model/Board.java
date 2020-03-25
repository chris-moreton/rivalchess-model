package com.netsensia.rivalchess.model;

import com.netsensia.rivalchess.model.util.BoardUtils;
import com.netsensia.rivalchess.model.util.FenUtils;
import com.netsensia.rivalchess.model.util.MoveMaker;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Board {

    private final Map<Square, SquareOccupant> squareOccupants;
    private final Map<SquareOccupant, List<Square>> occupantSquares;

    private final int enPassantFile;

    private final boolean isWhiteKingSideCastleAvailable;
    private final boolean isWhiteQueenSideCastleAvailable;
    private final boolean isBlackKingSideCastleAvailable;
    private final boolean isBlackQueenSideCastleAvailable;

    private final int halfMoveCount;
    private final Colour sideToMove;

    private final int fullMoveCount;

    public Board(final BoardBuilder builder) {
        this.squareOccupants = new EnumMap<>(builder.squareOccupants);
        this.enPassantFile = builder.enPassantFile;
        this.isWhiteQueenSideCastleAvailable = builder.isWhiteQueenSideCastleAvailable;
        this.isBlackQueenSideCastleAvailable = builder.isBlackQueenSideCastleAvailable;
        this.isWhiteKingSideCastleAvailable = builder.isWhiteKingSideCastleAvailable;
        this.isBlackKingSideCastleAvailable = builder.isBlackKingSideCastleAvailable;
        this.halfMoveCount = builder.halfMoveCount;
        this.sideToMove = builder.sideToMove;
        this.fullMoveCount = builder.fullMoveCount;

        this.occupantSquares = new EnumMap<>(SquareOccupant.class);

        populateOccupantSquares();
    }

    public static Board fromFen(final String fen) {
        return FenUtils.getBoardModel(fen);
    }

    public Map<Square, SquareOccupant> getSquareOccupants() {
        return new EnumMap<>(squareOccupants);
    }

    public Board(final Board board) {

        squareOccupants = new EnumMap<>(board.getSquareOccupants());

        this.sideToMove = board.getSideToMove();
        this.enPassantFile = board.getEnPassantFile();

        this.isBlackKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.BLACK);
        this.isBlackQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.BLACK);
        this.isWhiteKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.WHITE);
        this.isWhiteQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.WHITE);

        this.halfMoveCount = board.getHalfMoveCount();
        this.fullMoveCount = board.getFullMoveCount();

        this.occupantSquares = new EnumMap<>(SquareOccupant.class);

        populateOccupantSquares();
    }

    private void populateOccupantSquares() {

        for (SquareOccupant so : SquareOccupant.values()) {
            occupantSquares.put(so, new ArrayList<>());
        }

        for (Square s : Square.values()) {
            final SquareOccupant so = squareOccupants.get(s);
            if (so != SquareOccupant.NONE) {
                occupantSquares.get(so).add(s);
            }
        }
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

    public List<Square> getSquaresWithOccupant(SquareOccupant squareOccupant) {
        return new ArrayList<>(occupantSquares.get(squareOccupant));
    }

    public int getHalfMoveCount() {
        return this.halfMoveCount;
    }

    public int getFullMoveCount() {
        return this.fullMoveCount;
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

    public static BoardBuilder builder() {
        return new BoardBuilder();
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
                    this.getFullMoveCount() == bo.getFullMoveCount() &&
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
        private EnumMap<Square, SquareOccupant> squareOccupants;

        private int enPassantFile;

        private boolean isWhiteKingSideCastleAvailable;
        private boolean isWhiteQueenSideCastleAvailable;
        private boolean isBlackKingSideCastleAvailable;
        private boolean isBlackQueenSideCastleAvailable;

        private int halfMoveCount;
        private int fullMoveCount;

        private Colour sideToMove;

        private BoardBuilder() {
            enPassantFile = -1;
            isBlackQueenSideCastleAvailable = false;
            isWhiteQueenSideCastleAvailable = false;
            isWhiteKingSideCastleAvailable = false;
            isBlackKingSideCastleAvailable = false;
            halfMoveCount = 0;
            sideToMove = Colour.WHITE;

            squareOccupants = new EnumMap<>(Square.class);
            for (Square square : Square.values()) {
                squareOccupants.put(
                        square,
                        SquareOccupant.NONE
                );
            }
        }

        public BoardBuilder(final Board board) {
            squareOccupants = new EnumMap<>(board.getSquareOccupants());
            enPassantFile = board.getEnPassantFile();
            isWhiteKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.WHITE);
            isBlackKingSideCastleAvailable = board.isKingSideCastleAvailable(Colour.BLACK);
            isWhiteQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.WHITE);
            isBlackQueenSideCastleAvailable = board.isQueenSideCastleAvailable(Colour.BLACK);
            halfMoveCount = board.getHalfMoveCount();
            sideToMove = board.getSideToMove();
            fullMoveCount = board.getFullMoveCount();
        }

        public BoardBuilder withSquareOccupant(final Square square, final SquareOccupant squareOccupant) {
            this.squareOccupants.put(square, squareOccupant);
            return this;
        }

        public BoardBuilder withEnPassantFile(final int enPassantFile) {
            this.enPassantFile = enPassantFile;
            return this;
        }

        public BoardBuilder withFullMoveCount(final int fullMoveCount) {
            this.fullMoveCount = fullMoveCount;
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

        public BoardBuilder withNoCastleFlags() {
            this.isWhiteQueenSideCastleAvailable = false;
            this.isBlackQueenSideCastleAvailable = false;
            this.isWhiteKingSideCastleAvailable = false;
            this.isBlackKingSideCastleAvailable = false;
            return this;
        }

        public Board build() {
            return new Board(this);
        }

    }
}

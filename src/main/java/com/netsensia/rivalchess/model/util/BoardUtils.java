package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.helper.CastlingHelper;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.helper.KnightDirection;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.helper.PawnMoveHelper;
import com.netsensia.rivalchess.model.helper.SliderDirection;
import com.netsensia.rivalchess.model.Piece;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.netsensia.rivalchess.model.util.CommonUtils.isValidRankFileBoardReference;
import static com.netsensia.rivalchess.model.util.CommonUtils.isValidSquareReference;

public class BoardUtils {

    private BoardUtils() {}

    public static List<Square> getSquaresWithOccupant(Board board, SquareOccupant squareOccupant) {
        return board.squareOccupantStream()
                .filter(e -> e.getValue() == squareOccupant)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public static List<Move> getSliderMoves(final Board board, final Piece piece) {

        List<Square> fromSquares = getSquaresWithOccupant(board, piece.toSquareOccupant(board.getSideToMove()));

        final List<Move> moves = new ArrayList<>();

        for (Square fromSquare : fromSquares) {
            for (SliderDirection sliderDirection : SliderDirection.getDirectionsForPiece(piece)) {
                moves.addAll(
                        getDirectionalSquaresFromSquare(fromSquare, sliderDirection, board)
                                .stream()
                                .map(s -> new Move(fromSquare, s))
                                .collect(Collectors.toList()));
            }
        }

        return moves;
    }

    public static boolean directionIsValid(Square square, SliderDirection direction) {
        return isValidRankFileBoardReference(square.getXFile() + direction.getXIncrement()) &&
                isValidRankFileBoardReference(square.getYRank() + direction.getYIncrement());
    }

    public static List<Square> getDirectionalSquaresFromSquare(
            final Square square,
            final SliderDirection direction,
            final Board board) {

        final int nextX = square.getXFile() + direction.getXIncrement();
        final int nextY = square.getYRank() + direction.getYIncrement();

        if (!directionIsValid(square, direction)) {
            return new ArrayList<>();
        }

        final Square head = new Square(nextX, nextY);
        final SquareOccupant squareOccupant = board.getSquareOccupant(head);

        if (squareOccupant != SquareOccupant.NONE) {
            return squareOccupant.getColour() == board.getSideToMove()
                    ? new ArrayList<>()
                    : new ArrayList<>(Arrays.asList(head));
        }

        List<Square> tail = getDirectionalSquaresFromSquare(head, direction, board);
        tail.add(head);

        return tail;
    }

    public static List<Move> getKnightMoves(Board board) {
        final List<Move> moves = new ArrayList<>();

        List<Square> fromSquares = getSquaresWithOccupant(board, Piece.KNIGHT.toSquareOccupant(board.getSideToMove()));

        for (Square fromSquare : fromSquares) {
            for (KnightDirection knightDirection : KnightDirection.values()) {

                final int newX = fromSquare.getXFile() + knightDirection.getXIncrement();
                final int newY = fromSquare.getYRank() + knightDirection.getYIncrement();

                if (isValidSquareReference(newX, newY)) {
                    final Square targetSquare = new Square(newX, newY);

                    final SquareOccupant capturePiece = board.getSquareOccupant(targetSquare);
                    if (capturePiece == SquareOccupant.NONE || capturePiece.getColour() != board.getSideToMove()) {
                        moves.add(new Move(fromSquare, targetSquare));
                    }
                }
            }
        }

        return moves;
    }

    public static List<Move> getPawnMoves(Board board) {
        final List<Move> moves = new ArrayList<>();
        final Colour mover = board.getSideToMove();

        final List<Square> fromSquares =
                getSquaresWithOccupant(board, Piece.PAWN.toSquareOccupant(board.getSideToMove()));

        for (final Square fromSquare : fromSquares) {
            final Square oneSquareForward =
                    new Square(fromSquare.getXFile(),
                            fromSquare.getYRank() + PawnMoveHelper.advanceDirection(mover));

            if (board.getSquareOccupant(oneSquareForward) == SquareOccupant.NONE) {

                if (fromSquare.getYRank() == PawnMoveHelper.promotionRank(mover)) {
                    moves.addAll(getAllPromotionOptionsForMove(mover, fromSquare, oneSquareForward));
                } else {
                    moves.add(new Move(fromSquare, oneSquareForward));
                }

                if (fromSquare.getYRank() == PawnMoveHelper.homeRank(mover)) {
                    final Square twoSquaresForward =
                            new Square(fromSquare.getXFile(),
                                    PawnMoveHelper.homeRank(mover) + PawnMoveHelper.advanceDirection(mover) * 2);
                    if (board.getSquareOccupant(twoSquaresForward) == SquareOccupant.NONE) {
                        moves.add(new Move(fromSquare, twoSquaresForward));
                    }
                }
            }

            moves.addAll(getPawnCaptures(board, mover, fromSquare));
        }

        return moves;
    }

    private static List<Move> getAllPromotionOptionsForMove(Colour mover, Square fromSquare, Square toSquare) {
        List<Move> moves = new ArrayList<>();

        moves.add(new Move(fromSquare, toSquare, SquareOccupant.WQ.ofColour(mover)));
        moves.add(new Move(fromSquare, toSquare, SquareOccupant.WN.ofColour(mover)));
        moves.add(new Move(fromSquare, toSquare, SquareOccupant.WB.ofColour(mover)));
        moves.add(new Move(fromSquare, toSquare, SquareOccupant.WR.ofColour(mover)));

        return moves;
    }

    private static List<Move> getPawnCaptures(Board board, Colour mover, Square fromSquare) {

        final List<Move> moves = new ArrayList<>();

        for (SliderDirection captureDirection : Arrays.asList(SliderDirection.E, SliderDirection.W)) {
            moves.addAll(getPawnCapturesInDirection(board, mover, fromSquare, captureDirection));
        }
        return moves;
    }

    private static List<Move> getPawnCapturesInDirection(Board board, Colour mover, Square fromSquare, SliderDirection captureDirection) {

        final List<Move> moves = new ArrayList<>();

        final int captureX = fromSquare.getXFile() + captureDirection.getXIncrement();
        final int captureY = fromSquare.getYRank() + PawnMoveHelper.advanceDirection(mover);

        if (isValidSquareReference(captureX, captureY)) {
            final Square captureSquare = new Square(captureX, captureY);

            if (board.getSquareOccupant(captureSquare) == SquareOccupant.NONE) {
                if (board.getEnPassantFile() == captureSquare.getXFile() &&
                        captureSquare.getYRank() == PawnMoveHelper.enPassantToRank(mover)) {
                    moves.add(new Move(fromSquare, captureSquare));
                }
            } else if (board.getSquareOccupant(captureSquare).getColour() == mover.opponent()) {
                if (fromSquare.getYRank() == PawnMoveHelper.promotionRank(mover)) {
                    moves.addAll(getAllPromotionOptionsForMove(mover, fromSquare, captureSquare));
                } else {
                    moves.add(new Move(fromSquare, captureSquare));
                }
            }
        }

        return moves;
    }

    public static List<Move> getKingMoves(final Board board) {
        final Square fromSquare =
                getSquaresWithOccupant(board, Piece.KING.toSquareOccupant(board.getSideToMove())).get(0);

        final List<Move> moves = new ArrayList<>();

        for (SliderDirection sliderDirection : SliderDirection.getDirectionsForPiece(Piece.KING)) {
            final int newX = fromSquare.getXFile() + sliderDirection.getXIncrement();
            final int newY = fromSquare.getYRank() + sliderDirection.getYIncrement();

            if (isValidSquareReference(newX, newY)) {
                final Square targetSquare = new Square(newX, newY);

                final SquareOccupant capturePiece = board.getSquareOccupant(targetSquare);
                if (capturePiece == SquareOccupant.NONE || capturePiece.getColour() != board.getSideToMove()) {
                    moves.add(new Move(fromSquare, targetSquare));
                }
            }
        }

        moves.addAll(getCastlingMoves(board));

        return moves;
    }

    public static List<Move> getCastlingMoves(Board board) {

        final List<Move> moves = new ArrayList<>();
        final Colour colour = board.getSideToMove();

        if (board.isKingSideCastleAvailable(colour) &&
                board.getSquareOccupant(CastlingHelper.kingHome(colour)) == SquareOccupant.WK.ofColour(colour) &&
                board.getSquareOccupant(CastlingHelper.kingRookHome(colour)) == SquareOccupant.WR.ofColour(colour) &&
                board.getSquareOccupant(CastlingHelper.kingKnightHome(colour)) == SquareOccupant.NONE &&
                board.getSquareOccupant(CastlingHelper.kingBishopHome(colour)) == SquareOccupant.NONE &&
                !BoardUtils.isSquareAttacked(board, CastlingHelper.kingHome(colour), colour.opponent()) &&
                !BoardUtils.isSquareAttacked(board, CastlingHelper.kingKnightHome(colour), colour.opponent()) &&
                !BoardUtils.isSquareAttacked(board, CastlingHelper.kingBishopHome(colour), colour.opponent())
        ) {
            moves.add(new Move(CastlingHelper.kingHome(colour), CastlingHelper.kingKnightHome(colour)));
        }

        if (board.isQueenSideCastleAvailable(colour) &&
                board.getSquareOccupant(CastlingHelper.kingHome(colour)) == SquareOccupant.WK.ofColour(colour) &&
                board.getSquareOccupant(CastlingHelper.queenRookHome(colour)) == SquareOccupant.WR.ofColour(colour) &&
                board.getSquareOccupant(CastlingHelper.queenHome(colour)) == SquareOccupant.NONE &&
                board.getSquareOccupant(CastlingHelper.queenKnightHome(colour)) == SquareOccupant.NONE &&
                board.getSquareOccupant(CastlingHelper.queenBishopHome(colour)) == SquareOccupant.NONE &&
                !BoardUtils.isSquareAttacked(board, CastlingHelper.kingHome(colour), colour.opponent()) &&
                !BoardUtils.isSquareAttacked(board, CastlingHelper.queenBishopHome(colour), colour.opponent()) &&
                !BoardUtils.isSquareAttacked(board, CastlingHelper.queenHome(colour), colour.opponent())

        ) {
            moves.add(new Move(CastlingHelper.kingHome(colour), CastlingHelper.queenBishopHome(colour)));
        }

        return moves;
    }

    public static List<Move> getAllMovesWithoutRemovingChecks(final Board board) {
        List<Move> moves = new ArrayList<>();

        moves.addAll(getPawnMoves(board));
        moves.addAll(getSliderMoves(board, Piece.QUEEN));
        moves.addAll(getSliderMoves(board, Piece.BISHOP));
        moves.addAll(getSliderMoves(board, Piece.ROOK));
        moves.addAll(getKnightMoves(board));
        moves.addAll(getKingMoves(board));

        return moves;

    }

    public static boolean isSquareAttacked(final Board board, final Square square, final Colour byColour) {

        final Board newBoard = Board.copy(board);

        newBoard.setKingSideCastleAvailable(Colour.WHITE, false);
        newBoard.setKingSideCastleAvailable(Colour.BLACK, false);
        newBoard.setQueenSideCastleAvailable(Colour.WHITE, false);
        newBoard.setQueenSideCastleAvailable(Colour.BLACK, false);

        if (newBoard.getSquareOccupant(square) == SquareOccupant.NONE) {
            // put something there to capture
            newBoard.setSquareOccupant(square, SquareOccupant.BR.ofColour(byColour.opponent()));
        }

        newBoard.setSideToMove(byColour);

        final List<Move> moveList = getAllMovesWithoutRemovingChecks(newBoard);

        final List<Move> captureMoves =
                moveList.stream().filter(m -> m.getTgtBoardRef().equals(square)).collect(Collectors.toList());

        return !captureMoves.isEmpty();
    }

    public static boolean isCheck(final Board board) {

        final List<Square> squares = BoardUtils.getSquaresWithOccupant(
                board, SquareOccupant.WK.ofColour(board.getSideToMove()));

        if (squares.isEmpty()) {
            throw new RuntimeException(
                    "Given a board with no " + board.getSideToMove() + " king on it.");
        }

        final Square ourKingSquare = squares.get(0);

        return BoardUtils.isSquareAttacked(
                board,
                ourKingSquare, board.getSideToMove().opponent());

    }

    public static List<Move> getLegalMoves(final Board board) {
        final List<Move> moves = getAllMovesWithoutRemovingChecks(board);

        return moves.stream()
                .filter(m -> {
                    final Board newBoard = MoveMaker.makeMove(board, m);
                    // If it were still my move, would I be in check?
                    newBoard.setSideToMove(newBoard.getSideToMove().opponent());
                    return !BoardUtils.isCheck(newBoard);
                })
                .collect(Collectors.toList());
    }
}
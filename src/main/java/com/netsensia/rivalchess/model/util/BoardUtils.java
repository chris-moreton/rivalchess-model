package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.exception.InvalidBoardException;
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
import java.util.stream.Collectors;

import static com.netsensia.rivalchess.model.util.CommonUtils.isValidRankFileBoardReference;
import static com.netsensia.rivalchess.model.util.CommonUtils.isValidSquareReference;

public class BoardUtils {

    private BoardUtils() {}

    public static List<Move> getSliderMoves(final Board board, final Piece piece) {

        final List<Square> fromSquares =
                board.getSquaresWithOccupant(piece.toSquareOccupant(board.getSideToMove()));

        final List<Move> moves = new ArrayList<>();

        for (final Square fromSquare : fromSquares) {
            for (final SliderDirection sliderDirection : SliderDirection.getDirectionsForPiece(piece)) {
                moves.addAll(
                        getDirectionalSquaresFromSquare(fromSquare, sliderDirection, board)
                                .stream()
                                .map(s -> new Move(fromSquare, s))
                                .collect(Collectors.toList()));
            }
        }

        return moves;
    }

    public static List<Square> getDirectionalSquaresFromSquare(
            final Square square,
            final SliderDirection direction,
            final Board board) {

        final int nextX = square.getXFile() + direction.getXIncrement();
        final int nextY = square.getYRank() + direction.getYIncrement();

        if (!square.isValidDirection(direction)) {
            return new ArrayList<>();
        }

        final Square head = Square.fromCoords(nextX, nextY);
        final SquareOccupant squareOccupant = board.getSquareOccupant(head);

        if (squareOccupant != SquareOccupant.NONE) {
            return squareOccupant.getColour() == board.getSideToMove()
                    ? new ArrayList<>()
                    : new ArrayList<>(Arrays.asList(head));
        }

        final List<Square> tail = getDirectionalSquaresFromSquare(head, direction, board);
        tail.add(head);

        return tail;
    }

    public static List<Move> getKnightMoves(final Board board) {
        final List<Move> moves = new ArrayList<>();

        final List<Square> fromSquares = board.getSquaresWithOccupant(
                Piece.KNIGHT.toSquareOccupant(board.getSideToMove()));

        for (final Square fromSquare : fromSquares) {
            for (final KnightDirection knightDirection : KnightDirection.values()) {

                final int newX = fromSquare.getXFile() + knightDirection.getXIncrement();
                final int newY = fromSquare.getYRank() + knightDirection.getYIncrement();

                if (isValidSquareReference(newX, newY)) {
                    final Square targetSquare = Square.fromCoords(newX, newY);

                    final SquareOccupant capturePiece = board.getSquareOccupant(targetSquare);
                    if (capturePiece == SquareOccupant.NONE || capturePiece.getColour() != board.getSideToMove()) {
                        moves.add(new Move(fromSquare, targetSquare));
                    }
                }
            }
        }

        return moves;
    }

    public static List<Move> getPawnMoves(final Board board) {
        final List<Move> moves = new ArrayList<>();
        final Colour mover = board.getSideToMove();

        final List<Square> fromSquares =
                board.getSquaresWithOccupant(Piece.PAWN.toSquareOccupant(board.getSideToMove()));

        for (final Square fromSquare : fromSquares) {
            final Square oneSquareForward =
                    Square.fromCoords(fromSquare.getXFile(),
                            fromSquare.getYRank() + PawnMoveHelper.advanceDirection(mover));

            if (board.getSquareOccupant(oneSquareForward) == SquareOccupant.NONE) {

                if (fromSquare.getYRank() == PawnMoveHelper.promotionRank(mover)) {
                    moves.addAll(getAllPromotionOptionsForMove(mover, fromSquare, oneSquareForward));
                } else {
                    moves.add(new Move(fromSquare, oneSquareForward));
                }

                if (fromSquare.getYRank() == PawnMoveHelper.homeRank(mover)) {
                    final Square twoSquaresForward =
                            Square.fromCoords(fromSquare.getXFile(),
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

    private static List<Move> getAllPromotionOptionsForMove(
            final Colour mover,
            final Square fromSquare,
            final Square toSquare) {

        List<Move> moves = new ArrayList<>();

        moves.add(new Move(fromSquare, toSquare, SquareOccupant.WQ.ofColour(mover)));
        moves.add(new Move(fromSquare, toSquare, SquareOccupant.WN.ofColour(mover)));
        moves.add(new Move(fromSquare, toSquare, SquareOccupant.WB.ofColour(mover)));
        moves.add(new Move(fromSquare, toSquare, SquareOccupant.WR.ofColour(mover)));

        return moves;
    }

    private static List<Move> getPawnCaptures(
            final Board board,
            final Colour mover,
            final Square fromSquare) {

        final List<Move> moves = new ArrayList<>();

        for (SliderDirection captureDirection : PawnMoveHelper.getCaptureDirections()) {
            moves.addAll(getPawnCapturesInDirection(board, mover, fromSquare, captureDirection));
        }
        return moves;
    }

    private static List<Move> getPawnCapturesInDirection(
            final Board board,
            final Colour mover,
            final Square fromSquare,
            final SliderDirection captureDirection) {

        final List<Move> moves = new ArrayList<>();

        final int captureX = fromSquare.getXFile() + captureDirection.getXIncrement();
        final int captureY = fromSquare.getYRank() + PawnMoveHelper.advanceDirection(mover);

        if (isValidSquareReference(captureX, captureY)) {
            final Square captureSquare = Square.fromCoords(captureX, captureY);

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
                board.getSquaresWithOccupant(Piece.KING.toSquareOccupant(board.getSideToMove())).get(0);

        final List<Move> moves = new ArrayList<>();

        for (final SliderDirection sliderDirection : SliderDirection.getDirectionsForPiece(Piece.KING)) {
            final int newX = fromSquare.getXFile() + sliderDirection.getXIncrement();
            final int newY = fromSquare.getYRank() + sliderDirection.getYIncrement();

            if (isValidSquareReference(newX, newY)) {
                final Square targetSquare = Square.fromCoords(newX, newY);

                final SquareOccupant capturePiece = board.getSquareOccupant(targetSquare);
                if (capturePiece == SquareOccupant.NONE || capturePiece.getColour() != board.getSideToMove()) {
                    moves.add(new Move(fromSquare, targetSquare));
                }
            }
        }

        moves.addAll(getCastlingMoves(board));

        return moves;
    }

    public static List<Move> getCastlingMoves(final Board board) {

        final List<Move> moves = new ArrayList<>();
        final Colour colour = board.getSideToMove();

        if (board.isKingSideCastleAvailable(colour) &&
                board.getSquareOccupant(CastlingHelper.kingHome(colour)) == SquareOccupant.WK.ofColour(colour) &&
                board.getSquareOccupant(CastlingHelper.kingRookHome(colour)) == SquareOccupant.WR.ofColour(colour) &&
                board.getSquareOccupant(CastlingHelper.kingKnightHome(colour)) == SquareOccupant.NONE &&
                board.getSquareOccupant(CastlingHelper.kingBishopHome(colour)) == SquareOccupant.NONE &&
                !BoardUtils.isSquareAttackedBy(board, CastlingHelper.kingHome(colour), colour.opponent()) &&
                !BoardUtils.isSquareAttackedBy(board, CastlingHelper.kingKnightHome(colour), colour.opponent()) &&
                !BoardUtils.isSquareAttackedBy(board, CastlingHelper.kingBishopHome(colour), colour.opponent())
        ) {
            moves.add(new Move(CastlingHelper.kingHome(colour), CastlingHelper.kingKnightHome(colour)));
        }

        if (board.isQueenSideCastleAvailable(colour) &&
                board.getSquareOccupant(CastlingHelper.kingHome(colour)) == SquareOccupant.WK.ofColour(colour) &&
                board.getSquareOccupant(CastlingHelper.queenRookHome(colour)) == SquareOccupant.WR.ofColour(colour) &&
                board.getSquareOccupant(CastlingHelper.queenHome(colour)) == SquareOccupant.NONE &&
                board.getSquareOccupant(CastlingHelper.queenKnightHome(colour)) == SquareOccupant.NONE &&
                board.getSquareOccupant(CastlingHelper.queenBishopHome(colour)) == SquareOccupant.NONE &&
                !BoardUtils.isSquareAttackedBy(board, CastlingHelper.kingHome(colour), colour.opponent()) &&
                !BoardUtils.isSquareAttackedBy(board, CastlingHelper.queenBishopHome(colour), colour.opponent()) &&
                !BoardUtils.isSquareAttackedBy(board, CastlingHelper.queenHome(colour), colour.opponent())

        ) {
            moves.add(new Move(CastlingHelper.kingHome(colour), CastlingHelper.queenBishopHome(colour)));
        }

        return moves;
    }

    public static List<Move> getAllMovesWithoutRemovingChecks(final Board board) {

        final List<Move> moves = new ArrayList<>();

        moves.addAll(BoardUtils.getPawnMoves(board));
        moves.addAll(BoardUtils.getKnightMoves(board));
        moves.addAll(BoardUtils.getKingMoves(board));
        moves.addAll(BoardUtils.getSliderMoves(board, Piece.QUEEN));
        moves.addAll(BoardUtils.getSliderMoves(board, Piece.BISHOP));
        moves.addAll(BoardUtils.getSliderMoves(board, Piece.ROOK));

        return moves;
    }

    public static boolean isSquareAttackedBy(final Board board, final Square square, final Colour byColour) {

        return isSquareAttackedByKing(board, square, byColour) ||
                isSquareAttackedByKnight(board, square, byColour) ||
                isSquareAttackedByPawn(board, square, byColour) ||
                isSquareAttackedByBishopOrQueen(board, square, byColour) ||
                isSquareAttackedByRookOrQueen(board, square, byColour);

    }

    private static boolean isSquareAttackedByKing(Board board, Square square, Colour byColour) {
        for (final SliderDirection sliderDirection : SliderDirection.getDirectionsForPiece(Piece.KING)) {
            final int newX = square.getXFile() + sliderDirection.getXIncrement();
            final int newY = square.getYRank() + sliderDirection.getYIncrement();
            if (isValidSquareReference(newX, newY) &&
                    board.getSquareOccupant(Square.fromCoords(newX,newY)) == SquareOccupant.WK.ofColour(byColour)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSquareAttackedByKnight(Board board, Square square, Colour byColour) {
        for (final KnightDirection knightDirection : KnightDirection.values()) {
            final int newX = square.getXFile() + knightDirection.getXIncrement();
            final int newY = square.getYRank() + knightDirection.getYIncrement();
            if (isValidSquareReference(newX, newY)
                    && board.getSquareOccupant(Square.fromCoords(newX,newY)) == SquareOccupant.WN.ofColour(byColour)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSquareAttackedByPawn(Board board, Square square, Colour byColour) {
        for (SliderDirection captureDirection : PawnMoveHelper.getCaptureDirections()) {
            final int newX = square.getXFile() + captureDirection.getXIncrement();
            final int newY = square.getYRank() + PawnMoveHelper.advanceDirection(byColour.opponent());
            if (isValidSquareReference(newX, newY)
                    && board.getSquareOccupant(Square.fromCoords(newX,newY)) == SquareOccupant.WP.ofColour(byColour)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSquareAttackedByBishopOrQueen(Board board, Square square, Colour byColour) {
        for (SliderDirection sliderDirection : SliderDirection.getDirectionsForPiece(Piece.BISHOP)) {
            if (isSquareAttackedBySliderInDirection(board, square, byColour, sliderDirection, Piece.BISHOP)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSquareAttackedByRookOrQueen(Board board, Square square, Colour byColour) {
        for (SliderDirection sliderDirection : SliderDirection.getDirectionsForPiece(Piece.ROOK)) {
            if (isSquareAttackedBySliderInDirection(board, square, byColour, sliderDirection, Piece.ROOK)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSquareAttackedBySliderInDirection(
                Board board, Square square, Colour byColour, SliderDirection sliderDirection, Piece piece) {

        final int newX = square.getXFile() + sliderDirection.getXIncrement();
        final int newY = square.getYRank() + sliderDirection.getYIncrement();

        if (isValidSquareReference(newX, newY)) {
            final Square newSquare = Square.fromCoords(newX,newY);
            final SquareOccupant squareOccupant = board.getSquareOccupant(newSquare);
            if (squareOccupant == SquareOccupant.NONE) {
                return isSquareAttackedBySliderInDirection(board, newSquare, byColour, sliderDirection, piece);
            }
            if (squareOccupant == piece.toSquareOccupant(byColour) ||
                    squareOccupant == SquareOccupant.WQ.ofColour(byColour)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMoveLeavesMoverInCheck(final Board board, final Move move) {
        final Board.BoardBuilder boardBuilder =
                new Board.BoardBuilder(MoveMaker.makeMove(board, move))
                .withSideToMove(board.getSideToMove());

        try {
            return isCheck(boardBuilder.build());
        } catch (InvalidBoardException e) {
            throw new InvalidBoardException("After making trial move " + move + ", I caught " + e.getMessage());
        }
    }

    public static boolean isCheck(final Board board) {

        final List<Square> squares = board.getSquaresWithOccupant(
                SquareOccupant.WK.ofColour(board.getSideToMove()));

        if (squares.isEmpty()) {
            throw new InvalidBoardException(
                    "Given a board with no " + board.getSideToMove() + " king on it.\n" + board);
        }

        final Square ourKingSquare = squares.get(0);

        return BoardUtils.isSquareAttackedBy(
                board,
                ourKingSquare,
                board.getSideToMove().opponent());

    }

    public static List<Move> getLegalMoves(final Board board) {
        final List<Move> moves = getAllMovesWithoutRemovingChecks(board);

        return moves.parallelStream()
                .filter(m -> !BoardUtils.isMoveLeavesMoverInCheck(board, m))
                .collect(Collectors.toList());
    }
}
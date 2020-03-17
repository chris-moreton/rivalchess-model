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

    public static List<Square> getAttackersOfSquare(final Board board, final Square square) {
        return null;
    }

    public static List<Square> getSquaresWithOccupant(Board board, SquareOccupant squareOccupant) {
        return board.squareOccupantStream()
                .filter(e -> e.getValue() == squareOccupant)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public static List<Move> getMoves(final Square square) {
        return null;
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
                    moves.add(new Move(fromSquare, oneSquareForward, SquareOccupant.WQ.ofColour(mover)));
                    moves.add(new Move(fromSquare, oneSquareForward, SquareOccupant.WN.ofColour(mover)));
                    moves.add(new Move(fromSquare, oneSquareForward, SquareOccupant.WB.ofColour(mover)));
                    moves.add(new Move(fromSquare, oneSquareForward, SquareOccupant.WR.ofColour(mover)));
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

            final List<SliderDirection> captureDirections = Arrays.asList(
                    SliderDirection.E, SliderDirection.W
            );
            for (SliderDirection captureDirection : captureDirections) {
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
                            moves.add(new Move(fromSquare, captureSquare, SquareOccupant.WQ.ofColour(mover)));
                            moves.add(new Move(fromSquare, captureSquare, SquareOccupant.WN.ofColour(mover)));
                            moves.add(new Move(fromSquare, captureSquare, SquareOccupant.WB.ofColour(mover)));
                            moves.add(new Move(fromSquare, captureSquare, SquareOccupant.WR.ofColour(mover)));
                        } else {
                            moves.add(new Move(fromSquare, captureSquare));
                        }
                    }
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

        for (Colour colour : Colour.list()) {
            if (board.isKingSideCastleAvailable(colour) &&
                    board.getSquareOccupant(CastlingHelper.kingHome(colour)) == SquareOccupant.WK.ofColour(colour) &&
                    board.getSquareOccupant(CastlingHelper.kingRookHome(colour)) == SquareOccupant.WR.ofColour(colour) &&
                    board.getSquareOccupant(CastlingHelper.kingKnightHome(colour)) == SquareOccupant.NONE &&
                    board.getSquareOccupant(CastlingHelper.kingBishopHome(colour)) == SquareOccupant.NONE
            ) {
                moves.add(new Move(CastlingHelper.kingHome(colour), CastlingHelper.kingKnightHome(colour)));
            }

            if (board.isQueenSideCastleAvailable(colour) &&
                    board.getSquareOccupant(CastlingHelper.kingHome(colour)) == SquareOccupant.WK.ofColour(colour) &&
                    board.getSquareOccupant(CastlingHelper.queenRookHome(colour)) == SquareOccupant.WR.ofColour(colour) &&
                    board.getSquareOccupant(CastlingHelper.queenHome(colour)) == SquareOccupant.NONE &&
                    board.getSquareOccupant(CastlingHelper.queenKnightHome(colour)) == SquareOccupant.NONE &&
                    board.getSquareOccupant(CastlingHelper.queenBishopHome(colour)) == SquareOccupant.NONE
            ) {
                moves.add(new Move(CastlingHelper.kingHome(colour), CastlingHelper.queenBishopHome(colour)));
            }
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

    public static boolean isCheck(final Board board) {

        final Board newBoard = Board.copy(board);

        newBoard.setSideToMove(board.getSideToMove().opponent());

        final List<Move> moveList = getAllMovesWithoutRemovingChecks(newBoard);

        final Square ourKingSquare =
                BoardUtils.getSquaresWithOccupant(board, SquareOccupant.WK.ofColour(board.getSideToMove())).get(0);

        final List<Move> kingCaptureMoves =
                moveList.stream().filter(m -> m.getTgtBoardRef().equals(ourKingSquare)).collect(Collectors.toList());

        return kingCaptureMoves.size() > 0;
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
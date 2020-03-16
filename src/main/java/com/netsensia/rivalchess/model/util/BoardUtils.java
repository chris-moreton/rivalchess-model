package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.KnightDirection;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.SliderDirection;
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
    public static boolean isCheck(final Board board) {
        return true;
    }

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
        return null;
    }

    public static List<Move> getKingMoves(final Board board) {
        Square fromSquare =
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

        return moves;
    }

    public List<Move> getLegalMoves(Board board) {
        return null;
    }
}

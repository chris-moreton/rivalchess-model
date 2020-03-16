package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.MoveDirection;
import com.netsensia.rivalchess.model.Piece;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static List<Move> getBishopMoves(final Board board) {
        return getSliderMoves(board, Piece.BISHOP);
    }

    public static List<Move> getRookMoves(final Board board) {
        return getSliderMoves(board, Piece.ROOK);
    }

    public static List<Move> getQueenMoves(final Board board) {
        final List<Move> queenMoves = new ArrayList<>();
        queenMoves.addAll(getSliderMoves(board, Piece.ROOK));
        queenMoves.addAll(getSliderMoves(board, Piece.BISHOP));
        return queenMoves;
    }

    public static List<Move> getSliderMoves(final Board board, final Piece piece) {

        List<Square> fromSquares = getSquaresWithOccupant(board, piece.toSquareOccupant(board.getSideToMove()));

        final List<Move> moves = new ArrayList<>();

        for (Square fromSquare : fromSquares) {
            for (MoveDirection moveDirection : getDirectionsForPiece(piece)) {
                moves.addAll(
                        MoveUtils.getDirectionalSquaresFromSquare(fromSquare, moveDirection, board)
                                .stream()
                                .map(s -> new Move(fromSquare, s))
                                .collect(Collectors.toList()));
            }
        }

        return moves;
    }

    public static List<MoveDirection> getDirectionsForPiece(Piece piece) {
        switch (piece) {
            case KING:
            case QUEEN:
                return new ArrayList<>(Arrays.asList(
                        MoveDirection.NE, MoveDirection.NW, MoveDirection.SE, MoveDirection.SW,
                        MoveDirection.N, MoveDirection.W, MoveDirection.S, MoveDirection.E
                ));
            case BISHOP:
                return new ArrayList<>(Arrays.asList(
                        MoveDirection.NE, MoveDirection.NW, MoveDirection.SE, MoveDirection.SW
                ));
            case ROOK:
                return new ArrayList<>(Arrays.asList(
                        MoveDirection.N, MoveDirection.W, MoveDirection.S, MoveDirection.E
                ));
            default:
                throw new RuntimeException("Can't get directions for a non-sliding piece");
        }
    }
}

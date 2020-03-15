package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.MoveDirection;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public static List<Move> getBishopMoves(final Square square, final Board board) {
        List<Square> fromSquares = getSquaresWithOccupant(
                board, SquareOccupant.WB.ofColour(board.getSideToMove()));

        final List<MoveDirection> bishopDirections = new ArrayList<>(Arrays.asList(
                MoveDirection.NE, MoveDirection.NW, MoveDirection.SE, MoveDirection.SW
        ));

        final List<Move> bishopMoves = new ArrayList<>();

        for (Square fromSquare : fromSquares) {
            for (MoveDirection moveDirection : bishopDirections) {
                bishopMoves.addAll(
                        MoveUtils.getDirectionalSquaresFromSquare(fromSquare, moveDirection, board)
                        .stream()
                        .map(s -> new Move(fromSquare, s))
                        .collect(Collectors.toList()));
            }
        }

        return bishopMoves;
    }
}

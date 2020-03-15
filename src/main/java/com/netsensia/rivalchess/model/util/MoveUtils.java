package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.MoveDirection;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.netsensia.rivalchess.model.util.CommonUtils.isValidRankFileBoardReference;

public class MoveUtils {

    private MoveUtils() {}

    public static boolean directionIsValid(Square square, MoveDirection direction) {
        return isValidRankFileBoardReference(square.getXFile() + direction.getXIncrement()) &&
                isValidRankFileBoardReference(square.getYRank() + direction.getYIncrement());
    }

    public static List<Square> getDirectionalSquaresFromSquare(
            final Square square,
            final MoveDirection direction,
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

    public List<Move> getLegalMoves(Board board) {
        return null;
    }
}

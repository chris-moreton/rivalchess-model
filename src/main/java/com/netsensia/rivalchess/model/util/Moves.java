package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.MoveDirection;
import com.netsensia.rivalchess.model.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Moves {
    public static boolean isValidRankFileBoardReference(int n) {
        return (n >= 0 && n <= 7);
    }

    public static boolean directionIsValid(Square square, MoveDirection direction) {
        return isValidRankFileBoardReference(square.getXFile() + direction.getXIncrement()) &&
                isValidRankFileBoardReference(square.getYRank() + direction.getYIncrement());
    }

    public static List<Square> getDirectionalPotentialSquaresFromSquare(Square square, MoveDirection direction) {

        final int nextX = square.getXFile() + direction.getXIncrement();
        final int nextY = square.getYRank() + direction.getYIncrement();

        if (!directionIsValid(square, direction)) {
            return new ArrayList<Square>(Arrays.asList(new Square(nextX, nextY)));
        }

        return getDirectionalPotentialSquaresFromSquare(
                new Square(nextX, nextY), direction);
    }
}

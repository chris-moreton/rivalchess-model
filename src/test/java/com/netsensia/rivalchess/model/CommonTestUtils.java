package com.netsensia.rivalchess.model;

public class CommonTestUtils {
    public static Board getStartBoard() {
        final Board board = new Board();

        board.setSquareOccupant(new Square(0,0), SquareOccupant.BR);
        board.setSquareOccupant(new Square(1,0), SquareOccupant.BN);
        board.setSquareOccupant(new Square(2,0), SquareOccupant.BB);
        board.setSquareOccupant(new Square(3,0), SquareOccupant.BQ);
        board.setSquareOccupant(new Square(4,0), SquareOccupant.BK);
        board.setSquareOccupant(new Square(5,0), SquareOccupant.BB);
        board.setSquareOccupant(new Square(6,0), SquareOccupant.BN);
        board.setSquareOccupant(new Square(7,0), SquareOccupant.BR);

        board.setSquareOccupant(new Square(0,1), SquareOccupant.BP);
        board.setSquareOccupant(new Square(1,1), SquareOccupant.BP);
        board.setSquareOccupant(new Square(2,1), SquareOccupant.BP);
        board.setSquareOccupant(new Square(3,1), SquareOccupant.BP);
        board.setSquareOccupant(new Square(4,1), SquareOccupant.BP);
        board.setSquareOccupant(new Square(5,1), SquareOccupant.BP);
        board.setSquareOccupant(new Square(6,1), SquareOccupant.BP);
        board.setSquareOccupant(new Square(7,1), SquareOccupant.BP);

        board.setSquareOccupant(new Square(0,7), SquareOccupant.WR);
        board.setSquareOccupant(new Square(1,7), SquareOccupant.WN);
        board.setSquareOccupant(new Square(2,7), SquareOccupant.WB);
        board.setSquareOccupant(new Square(3,7), SquareOccupant.WQ);
        board.setSquareOccupant(new Square(4,7), SquareOccupant.WK);
        board.setSquareOccupant(new Square(5,7), SquareOccupant.WB);
        board.setSquareOccupant(new Square(6,7), SquareOccupant.WN);
        board.setSquareOccupant(new Square(7,7), SquareOccupant.WR);

        board.setSquareOccupant(new Square(0,6), SquareOccupant.WP);
        board.setSquareOccupant(new Square(1,6), SquareOccupant.WP);
        board.setSquareOccupant(new Square(2,6), SquareOccupant.WP);
        board.setSquareOccupant(new Square(3,6), SquareOccupant.WP);
        board.setSquareOccupant(new Square(4,6), SquareOccupant.WP);
        board.setSquareOccupant(new Square(5,6), SquareOccupant.WP);
        board.setSquareOccupant(new Square(6,6), SquareOccupant.WP);
        board.setSquareOccupant(new Square(7,6), SquareOccupant.WP);

        board.setSideToMove(Colour.WHITE);
        return board;
    }
}

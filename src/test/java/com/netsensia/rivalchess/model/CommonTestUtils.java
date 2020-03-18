package com.netsensia.rivalchess.model;

public class CommonTestUtils {
    public static Board getStartBoard() {
        final Board board = new Board();

        board.setSquareOccupant(Square.fromCoords(0,0), SquareOccupant.BR);
        board.setSquareOccupant(Square.fromCoords(1,0), SquareOccupant.BN);
        board.setSquareOccupant(Square.fromCoords(2,0), SquareOccupant.BB);
        board.setSquareOccupant(Square.fromCoords(3,0), SquareOccupant.BQ);
        board.setSquareOccupant(Square.fromCoords(4,0), SquareOccupant.BK);
        board.setSquareOccupant(Square.fromCoords(5,0), SquareOccupant.BB);
        board.setSquareOccupant(Square.fromCoords(6,0), SquareOccupant.BN);
        board.setSquareOccupant(Square.fromCoords(7,0), SquareOccupant.BR);

        board.setSquareOccupant(Square.fromCoords(0,1), SquareOccupant.BP);
        board.setSquareOccupant(Square.fromCoords(1,1), SquareOccupant.BP);
        board.setSquareOccupant(Square.fromCoords(2,1), SquareOccupant.BP);
        board.setSquareOccupant(Square.fromCoords(3,1), SquareOccupant.BP);
        board.setSquareOccupant(Square.fromCoords(4,1), SquareOccupant.BP);
        board.setSquareOccupant(Square.fromCoords(5,1), SquareOccupant.BP);
        board.setSquareOccupant(Square.fromCoords(6,1), SquareOccupant.BP);
        board.setSquareOccupant(Square.fromCoords(7,1), SquareOccupant.BP);

        board.setSquareOccupant(Square.fromCoords(0,7), SquareOccupant.WR);
        board.setSquareOccupant(Square.fromCoords(1,7), SquareOccupant.WN);
        board.setSquareOccupant(Square.fromCoords(2,7), SquareOccupant.WB);
        board.setSquareOccupant(Square.fromCoords(3,7), SquareOccupant.WQ);
        board.setSquareOccupant(Square.fromCoords(4,7), SquareOccupant.WK);
        board.setSquareOccupant(Square.fromCoords(5,7), SquareOccupant.WB);
        board.setSquareOccupant(Square.fromCoords(6,7), SquareOccupant.WN);
        board.setSquareOccupant(Square.fromCoords(7,7), SquareOccupant.WR);

        board.setSquareOccupant(Square.fromCoords(0,6), SquareOccupant.WP);
        board.setSquareOccupant(Square.fromCoords(1,6), SquareOccupant.WP);
        board.setSquareOccupant(Square.fromCoords(2,6), SquareOccupant.WP);
        board.setSquareOccupant(Square.fromCoords(3,6), SquareOccupant.WP);
        board.setSquareOccupant(Square.fromCoords(4,6), SquareOccupant.WP);
        board.setSquareOccupant(Square.fromCoords(5,6), SquareOccupant.WP);
        board.setSquareOccupant(Square.fromCoords(6,6), SquareOccupant.WP);
        board.setSquareOccupant(Square.fromCoords(7,6), SquareOccupant.WP);

        board.setSideToMove(Colour.WHITE);
        return board;
    }
}

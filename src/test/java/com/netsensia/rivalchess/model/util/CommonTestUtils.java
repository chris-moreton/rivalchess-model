package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;

public class CommonTestUtils {
    public static Board getStartBoard() {
        final Board.BoardBuilder boardBuilder = new Board.BoardBuilder();

        boardBuilder.withSquareOccupant(Square.fromCoords(0,0), SquareOccupant.BR);
        boardBuilder.withSquareOccupant(Square.fromCoords(1,0), SquareOccupant.BN);
        boardBuilder.withSquareOccupant(Square.fromCoords(2,0), SquareOccupant.BB);
        boardBuilder.withSquareOccupant(Square.fromCoords(3,0), SquareOccupant.BQ);
        boardBuilder.withSquareOccupant(Square.fromCoords(4,0), SquareOccupant.BK);
        boardBuilder.withSquareOccupant(Square.fromCoords(5,0), SquareOccupant.BB);
        boardBuilder.withSquareOccupant(Square.fromCoords(6,0), SquareOccupant.BN);
        boardBuilder.withSquareOccupant(Square.fromCoords(7,0), SquareOccupant.BR);

        boardBuilder.withSquareOccupant(Square.fromCoords(0,1), SquareOccupant.BP);
        boardBuilder.withSquareOccupant(Square.fromCoords(1,1), SquareOccupant.BP);
        boardBuilder.withSquareOccupant(Square.fromCoords(2,1), SquareOccupant.BP);
        boardBuilder.withSquareOccupant(Square.fromCoords(3,1), SquareOccupant.BP);
        boardBuilder.withSquareOccupant(Square.fromCoords(4,1), SquareOccupant.BP);
        boardBuilder.withSquareOccupant(Square.fromCoords(5,1), SquareOccupant.BP);
        boardBuilder.withSquareOccupant(Square.fromCoords(6,1), SquareOccupant.BP);
        boardBuilder.withSquareOccupant(Square.fromCoords(7,1), SquareOccupant.BP);

        boardBuilder.withSquareOccupant(Square.fromCoords(0,7), SquareOccupant.WR);
        boardBuilder.withSquareOccupant(Square.fromCoords(1,7), SquareOccupant.WN);
        boardBuilder.withSquareOccupant(Square.fromCoords(2,7), SquareOccupant.WB);
        boardBuilder.withSquareOccupant(Square.fromCoords(3,7), SquareOccupant.WQ);
        boardBuilder.withSquareOccupant(Square.fromCoords(4,7), SquareOccupant.WK);
        boardBuilder.withSquareOccupant(Square.fromCoords(5,7), SquareOccupant.WB);
        boardBuilder.withSquareOccupant(Square.fromCoords(6,7), SquareOccupant.WN);
        boardBuilder.withSquareOccupant(Square.fromCoords(7,7), SquareOccupant.WR);

        boardBuilder.withSquareOccupant(Square.fromCoords(0,6), SquareOccupant.WP);
        boardBuilder.withSquareOccupant(Square.fromCoords(1,6), SquareOccupant.WP);
        boardBuilder.withSquareOccupant(Square.fromCoords(2,6), SquareOccupant.WP);
        boardBuilder.withSquareOccupant(Square.fromCoords(3,6), SquareOccupant.WP);
        boardBuilder.withSquareOccupant(Square.fromCoords(4,6), SquareOccupant.WP);
        boardBuilder.withSquareOccupant(Square.fromCoords(5,6), SquareOccupant.WP);
        boardBuilder.withSquareOccupant(Square.fromCoords(6,6), SquareOccupant.WP);
        boardBuilder.withSquareOccupant(Square.fromCoords(7,6), SquareOccupant.WP);

        boardBuilder.withSideToMove(Colour.WHITE);
        return boardBuilder.build();
    }
}

package com.netsensia.rivalchess.model.helper;

import com.netsensia.rivalchess.model.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SliderDirection {
    N (0,-1),
    NE (1,-1),
    E (1,0),
    SE (1,1),
    S (0,1),
    SW (-1,1),
    W (-1,0),
    NW (-1,-1)
    ;

    private final int xIncrement;
    private final int yIncrement;

    private SliderDirection(final int xIncrement, final int yIncrement) {
        this.xIncrement = xIncrement;
        this.yIncrement = yIncrement;
    }

    public int getXIncrement() {
        return this.xIncrement;
    }

    public int getYIncrement() {
        return this.yIncrement;
    }

    public static List<SliderDirection> getDirectionsForPiece(Piece piece) {
        switch (piece) {
            case KING:
            case QUEEN:
                return new ArrayList<>(Arrays.asList(
                        SliderDirection.NE, SliderDirection.NW, SliderDirection.SE, SliderDirection.SW,
                        SliderDirection.N, SliderDirection.W, SliderDirection.S, SliderDirection.E
                ));
            case BISHOP:
                return new ArrayList<>(Arrays.asList(
                        SliderDirection.NE, SliderDirection.NW, SliderDirection.SE, SliderDirection.SW
                ));
            case ROOK:
                return new ArrayList<>(Arrays.asList(
                        SliderDirection.N, SliderDirection.W, SliderDirection.S, SliderDirection.E
                ));
            default:
                throw new RuntimeException("Can't get directions for a non-sliding piece");
        }
    }
}

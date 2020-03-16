package com.netsensia.rivalchess.model.util;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Colour;
import com.netsensia.rivalchess.model.CommonTestUtils;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.Piece;
import com.netsensia.rivalchess.model.SliderDirection;
import com.netsensia.rivalchess.model.Square;
import com.netsensia.rivalchess.model.SquareOccupant;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MoveMakerTest {

    @Test
    public void testWhitePromotionMove() {
        final Board board = CommonTestUtils.getStartBoard();

        board.setSideToMove(Colour.WHITE);

        board.setSquareOccupant(1, 1, SquareOccupant.WP);

        final Board newBoard = MoveMaker.makeMove(
                board, new Move(1,1,0,0, SquareOccupant.WB));

        assertEquals(SquareOccupant.WB, newBoard.getSquareOccupant(0,0));
        assertEquals(SquareOccupant.NONE, newBoard.getSquareOccupant(1,1));
    }
}
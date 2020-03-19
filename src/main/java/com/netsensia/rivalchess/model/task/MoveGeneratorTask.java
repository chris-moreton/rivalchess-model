package com.netsensia.rivalchess.model.task;

import com.netsensia.rivalchess.model.Board;
import com.netsensia.rivalchess.model.Move;
import com.netsensia.rivalchess.model.Piece;
import com.netsensia.rivalchess.model.exception.EnumSwitchException;
import com.netsensia.rivalchess.model.util.BoardUtils;

import java.util.List;
import java.util.concurrent.Callable;

public class MoveGeneratorTask implements Callable<List<Move>> {

    final Piece piece;
    final Board board;

    public MoveGeneratorTask(final Board board, final Piece piece) {
        this.board = board;
        this.piece = piece;
    }

    @Override
    public List<Move> call() {
        switch (piece) {
            case PAWN:
                return BoardUtils.getPawnMoves(board);
            case KNIGHT:
                return BoardUtils.getKnightMoves(board);
            case BISHOP:
            case QUEEN:
            case ROOK:
                return BoardUtils.getSliderMoves(board, piece);
            case KING:
                return BoardUtils.getKingMoves(board);
            default:
                throw new EnumSwitchException("Unknown Piece " + piece);
        }
    }
}

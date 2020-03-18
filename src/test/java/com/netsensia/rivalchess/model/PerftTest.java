package com.netsensia.rivalchess.model;

import static org.junit.Assert.assertEquals;

import com.netsensia.rivalchess.model.exception.IllegalFenException;
import com.netsensia.rivalchess.model.exception.InvalidMoveException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Objects;

public class PerftTest {

    private static final int MAX_PERFT_DEPTH = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(PerftTest.class);

    public static long getPerft(Board board, int depth) throws InvalidMoveException {
        if (depth == 0) {
            return 1;
        }
        long nodes = 0;
        int moveNum = 0;

        for (Move move : board.getLegalMoves()) {
            Board newBoard = Board.fromMove(board, move);
            nodes += getPerft(newBoard, depth - 1);
            moveNum++;
        }

        return nodes;
    }

    private void assertPerftScore(
            final String fen,
            final int depth,
            final int expectedScore) throws IllegalFenException, InvalidMoveException {

        Board board = Board.fromFen(fen);

        long start = System.currentTimeMillis();
        long nodes = getPerft(board, depth);

        assertEquals(expectedScore, nodes);

        long end = System.currentTimeMillis();
        long elapsed = end - start;
        double seconds = elapsed / 1000.0;
        double nps = nodes / (seconds < 1 ? 1 : seconds);

        if (LOGGER.isInfoEnabled()) {
            NumberFormat nf = NumberFormat.getInstance();
            String nodesPerSecond = nf.format(nps);
            LOGGER.info("Nodes per second = {}", nodesPerSecond);
        }
    }

    private void epdPerftSuiteToDepth(final int atDepth) throws IOException, InvalidMoveException {

        if (atDepth > MAX_PERFT_DEPTH) {
            return;
        }

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("perft.epd")).getFile());

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {

            if (!line.trim().equals("")) {
                final String[] parts = line.trim().split(";");
                final String fen = parts[0];
                final int availableDepths = parts.length - 1;
                if (atDepth <= availableDepths) {
                    final String[] nodeCountSplit = parts[atDepth].trim().split(" ");
                    final int depth = Integer.valueOf(nodeCountSplit[0].trim().replace("D", ""));
                    final int nodeCount = Integer.valueOf(nodeCountSplit[1]);
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("Testing " + fen + " for " + nodeCount + " nodes at depth " + depth);
                    }
                    assertPerftScore(fen, depth, nodeCount);
                }
            }
        }
        fr.close();

        epdPerftSuiteToDepth(atDepth + 1);
    }

    @Test
    public void epdPerftSuite() throws IOException, InvalidMoveException {
        epdPerftSuiteToDepth(1);
    }
}
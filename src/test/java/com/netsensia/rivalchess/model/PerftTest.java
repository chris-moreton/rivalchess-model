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

    private void assertPerftScore(String fen, int depth, int expectedScore) throws IllegalFenException, InvalidMoveException {

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

    @Test
    public void testPerftScoreSuperShort() throws IllegalFenException, InvalidMoveException {
        assertPerftScore("5k2/5p1p/p3B1p1/P5P1/3K1P1P/8/8/8 b - -", 2, 169);
    }

    @Test
    public void testPerftScoreShort() throws IllegalFenException, InvalidMoveException {
        assertPerftScore("5k2/5p1p/p3B1p1/P5P1/3K1P1P/8/8/8 b - -", 4, 20541);
        assertPerftScore("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 5, 4865609);
        assertPerftScore("8/3K4/2p5/p2b2r1/5k2/8/8/1q6 b - - 1 67", 2, 279);
        assertPerftScore("n1n5/PPPk4/8/8/8/8/4Kppp/5N1N b - - 0 1", 4, 182838);
    }

    @Test
    public void testPerftScoreLong() throws IllegalFenException, InvalidMoveException {
        assertPerftScore("8/7p/p5pb/4k3/P1pPn3/8/P5PP/1rB2RK1 b - d3 0 28", 6, 38633283);
        assertPerftScore("rnbqkb1r/ppppp1pp/7n/4Pp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3", 5, 11139762);
        assertPerftScore("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -", 7, 178633661);
        assertPerftScore("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -", 5, 193690690);
    }

    private void epdPerftSuiteToDepth(final int atDepth) throws IOException, InvalidMoveException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("perft.epd")).getFile());

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            if (!line.trim().equals("")) {
                final String[] parts = line.split(";");
                final String fen = parts[0];
                final int availableDepths = parts.length - 1;
                if (atDepth <= availableDepths) {
                    final String[] nodeCountSplit = parts[atDepth].split(" ");
                    final int depth = Integer.valueOf(nodeCountSplit[0].trim().replace("D", ""));
                    final int nodeCount = Integer.valueOf(nodeCountSplit[1]);
                    System.out.println("Testing " + fen + " for " + nodeCount + " nodes at depth " + depth);
                    assertPerftScore(fen, depth, nodeCount);
                }
            }
        }
        fr.close();
    }
    @Test
    public void epdPerftSuite() throws IOException, InvalidMoveException {

        epdPerftSuiteToDepth(1);
    }
}
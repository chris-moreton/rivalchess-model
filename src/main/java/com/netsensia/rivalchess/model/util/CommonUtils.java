package com.netsensia.rivalchess.model.util;

public class CommonUtils {
    public static boolean isValidRankFileBoardReference(int n) {
        return (n >= 0 && n <= 7);
    }

    public static boolean isValidSquareReference(int x, int y) {
        return isValidRankFileBoardReference(x) && isValidRankFileBoardReference(y);
    }
}

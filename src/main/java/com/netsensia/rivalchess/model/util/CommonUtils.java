package com.netsensia.rivalchess.model.util;

public class CommonUtils {

    private CommonUtils() {}

    public static boolean isValidRankFileBoardReference(final int n) {
        return (n >= 0 && n <= 7);
    }

    public static boolean isValidSquareReference(final int x, final int y) {
        return isValidRankFileBoardReference(x) && isValidRankFileBoardReference(y);
    }
}

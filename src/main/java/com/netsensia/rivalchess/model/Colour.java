package com.netsensia.rivalchess.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Colour {
    WHITE (0),
    BLACK  (1)
    ;

    private int value;

    private Colour(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Colour opponent() {
        return this == Colour.WHITE ? Colour.BLACK : Colour.WHITE;
    }

    public static List<Colour> list() {
        return new ArrayList<>(Arrays.asList(Colour.WHITE, Colour.BLACK));
    }
}
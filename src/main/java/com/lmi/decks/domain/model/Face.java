package com.lmi.decks.domain.model;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Face {

    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13);

    private int height;

    Face(int height) {
        this.height = height;
    }

    int getHeight() {
        return height;
    }

    static Stream<Face> stream() {
        return Arrays.stream(Face.values());
    }

}

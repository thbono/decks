package com.lmi.decks.domain.model;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Suit {

    HEARTS(1),
    SPADES(2),
    CLUBS(4),
    DIAMONDS(5);

    private int height;

    Suit(int height) {
        this.height = height;
    }

    static Stream<Suit> stream() {
        return Arrays.stream(Suit.values());
    }

}

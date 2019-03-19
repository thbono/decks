package com.lmi.decks.domain.model;

import org.junit.Test;

public class GameTest {

    @Test(expected = UnsupportedOperationException.class)
    public void getDecks() {
        new Game().getDecks().add(new Deck());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddDeckWhenDeckIsNull() {
        new Game().addDeck(null);
    }
}
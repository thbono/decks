package com.lmi.decks.domain.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GameTest {

    @Test(expected = UnsupportedOperationException.class)
    public void getDecks() {
        new Game().getDecks().add(new Deck());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddDeckWhenDeckIsNull() {
        new Game().addDeck(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addAndGetPlayers() {
        final Game game = new Game().addPlayer();
        final List<Player> players = game.getPlayers();
        Assert.assertFalse(players.isEmpty());
        Assert.assertEquals("Player 1", players.get(0).getName());
        players.add(new Player());
    }

    @Test
    public void removePlayer() {
        Assert.assertTrue(new Game().removePlayerById(1L).getPlayers().isEmpty());
    }

}
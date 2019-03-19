package com.lmi.decks.domain.model;

import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void createPlayer() {
        Assert.assertEquals("Player 1", new Player(1).getName());
        Assert.assertEquals("Player 2", new Player(2).getName());
    }

}
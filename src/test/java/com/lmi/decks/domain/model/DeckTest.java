package com.lmi.decks.domain.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeckTest {

    @Test(expected = UnsupportedOperationException.class)
    public void build() {
        final List<Card> cards = Deck.build().getCards();
        Assert.assertEquals(Deck.NUMBER_OF_CARDS, cards.size());
        final Map<Suit, List<Card>> suits = cards.stream().collect(Collectors.groupingBy(Card::getSuit));
        Assert.assertEquals(4, suits.size());
        suits.forEach((k,v) -> Assert.assertEquals(13, v.size()));
        cards.remove(0); // UnsupportedOperationException
    }

}
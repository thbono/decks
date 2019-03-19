package com.lmi.decks.domain.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int position;

    private Suit suit;

    private Face face;

    public Card() {
    }

    Card(int position, Suit suit, Face face) {
        this.position = position;
        this.suit = suit;
        this.face = face;
    }

    Suit getSuit() {
        return suit;
    }

}

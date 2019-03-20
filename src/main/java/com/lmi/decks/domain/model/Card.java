package com.lmi.decks.domain.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Card implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    private int position;

    private Suit suit;

    private Face face;

    @JsonIgnore
    private Long playerId;

    public Card() {
    }

    Card(int position, Suit suit, Face face) {
        this.position = position;
        this.suit = suit;
        this.face = face;
    }

    public Suit getSuit() {
        return suit;
    }

    @JsonIgnore
    public Integer getHeight() {
        return face.getHeight();
    }

    @JsonIgnore
    public boolean isOnDeck() {
        return playerId == null;
    }

    public boolean isDealtToPlayer(final Long playerId) {
        return this.playerId != null && this.playerId.equals(playerId);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPlayerId(final Long playerId) {
        this.playerId = playerId;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Card)) return -1;

        final Card other = (Card) o;
        return this.suit.getHeight() == other.suit.getHeight()
                ? Integer.compare(other.face.getHeight(), this.face.getHeight())
                : Integer.compare(this.suit.getHeight(), other.suit.getHeight());
    }

}

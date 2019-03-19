package com.lmi.decks.domain.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Deck {

    public static final int NUMBER_OF_CARDS = 52;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    private Deck(List<Card> cards) {
        this.cards = cards;
    }

    List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public static Deck build() {
        final List<Card> l = new ArrayList<>();
        final PrimitiveIterator.OfInt p = IntStream.rangeClosed(1, NUMBER_OF_CARDS).iterator();
        Face.stream().forEach(
                f -> l.addAll(Suit.stream().map(s -> new Card(p.nextInt(), s, f)).collect(Collectors.toList()))
        );
        return new Deck(l);
    }

}

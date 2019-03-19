package com.lmi.decks.domain.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime started = LocalDateTime.now();

    @Fetch(FetchMode.JOIN)
    @OneToMany(fetch = FetchType.EAGER)
    private List<Deck> decks = new ArrayList<>();

    public Game addDeck(final Deck deck) {
        decks.add(Optional.ofNullable(deck).orElseThrow(() -> new IllegalArgumentException("Deck cannot be null")));
        return this;
    }

}

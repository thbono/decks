package com.lmi.decks.domain.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public List<Deck> getDecks() {
        return Collections.unmodifiableList(decks);
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    @JsonIgnore
    public Stream<Card> getCardStream() {
        return decks.stream().flatMap(d -> d.getCards().stream());
    }

    public Game addDeck(final Deck deck) {
        decks.add(Optional.ofNullable(deck).orElseThrow(() -> new IllegalArgumentException("Deck cannot be null")));
        return this;
    }

    public Game addPlayer() {
        players.add(new Player(players.size() + 1));
        return this;
    }

    public Game removePlayerById(final long playerId) {
        final int index = IntStream.range(0, players.size()).filter(i -> players.get(i).getId().equals(playerId)).findFirst().orElse(-1);
        if (index >= 0) players.remove(index);
        return this;
    }

}

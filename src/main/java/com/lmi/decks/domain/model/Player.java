package com.lmi.decks.domain.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    Player() {
        name = "Unknown";
    }

    Player(final int player) {
        name = String.format("Player %d", player);
    }

    Long getId() {
        return id;
    }

    String getName() {
        return name;
    }
}

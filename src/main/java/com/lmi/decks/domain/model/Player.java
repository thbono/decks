package com.lmi.decks.domain.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Transient
    private Integer total;

    Player() {
        name = "Unknown";
    }

    Player(final int player) {
        name = String.format("Player %d", player);
    }

    public Long getId() {
        return id;
    }

    String getName() {
        return name;
    }

    public void setTotal(final Integer total) {
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }
}

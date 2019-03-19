package com.lmi.decks.controller;

import com.lmi.decks.domain.model.Deck;
import com.lmi.decks.domain.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class DeckController {

    @Autowired
    private DeckRepository repository;

    @PostMapping("/decks")
    public ResponseEntity<Deck> create() {
        final Deck deck = repository.save(Deck.build());
        return ResponseEntity.created(URI.create("/decks" + deck.getId())).body(deck);
    }

}

package com.lmi.decks.controller;

import com.lmi.decks.domain.model.Deck;
import com.lmi.decks.domain.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeckController {

    @Autowired
    private DeckRepository repository;

    @PostMapping("/decks")
    public Deck create() {
        return repository.save(Deck.build());
    }

}

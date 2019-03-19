package com.lmi.decks.controller;

import com.lmi.decks.domain.model.Game;
import com.lmi.decks.domain.repository.GameRepository;
import com.lmi.decks.domain.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    @Autowired
    private GameRepository repository;

    @Autowired
    private GameService service;

    @PostMapping("/games")
    public Game create() {
        return repository.save(new Game());
    }

    @GetMapping("/games")
    public Iterable<Game> findAll() {
        return repository.findAll();
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/games/{gameId}/decks/{deckId}")
    public ResponseEntity<Game> pinDeck(@PathVariable("gameId") Long gameId, @PathVariable("deckId") Long deckId) {
        try {
            return ResponseEntity.ok(service.pinDeck(gameId, deckId));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

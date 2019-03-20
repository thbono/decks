package com.lmi.decks.controller;

import com.lmi.decks.domain.model.Card;
import com.lmi.decks.domain.model.Game;
import com.lmi.decks.domain.model.Player;
import com.lmi.decks.domain.model.Suit;
import com.lmi.decks.domain.repository.GameRepository;
import com.lmi.decks.domain.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class GameController {

    @Autowired
    private GameRepository repository;

    @Autowired
    private GameService service;

    @PostMapping("/games")
    public ResponseEntity<Game> create() {
        final Game game = repository.save(new Game());
        return ResponseEntity.created(URI.create("/games" + game.getId())).body(game);
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

    @PostMapping("/games/{id}/players")
    public ResponseEntity<Game> addPlayer(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(service.addPlayer(id));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/games/{gameId}/players/{playerId}")
    public ResponseEntity<Game> removePlayer(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId) {
        try {
            return ResponseEntity.ok(service.removePlayer(gameId, playerId));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/games/{gameId}/players/{playerId}/cards")
    public ResponseEntity<Game> dealCard(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId) {
        try {
            return ResponseEntity.ok(service.dealCard(gameId, playerId));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/games/{gameId}/players/{playerId}/cards")
    public ResponseEntity<List<Card>> getPlayerCards(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId) {
        try {
            return ResponseEntity.ok(service.getPlayerCards(gameId, playerId));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/games/{id}/players")
    public ResponseEntity<List<Player>> getPlayers(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(service.getPlayers(id));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/games/{id}/suits")
    public ResponseEntity<Map<Suit, Long>> getSuits(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(service.getSuits(id));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/games/{id}/cards")
    public ResponseEntity<List<Card>> getCards(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(service.getCards(id));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/games/{id}/shuffle")
    public ResponseEntity<Game> shuffle(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(service.shuffle(id));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

package com.lmi.decks.domain.service;

import com.lmi.decks.domain.model.*;
import com.lmi.decks.domain.repository.DeckRepository;
import com.lmi.decks.domain.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

    @Autowired
    private DeckRepository deckRepository;

    @Transactional
    public Game pinDeck(final Long gameId, final Long deckId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        final Deck deck = deckRepository.findById(deckId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return repository.save(game.addDeck(deck));
    }

    @Transactional
    public Game addPlayer(final Long gameId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return repository.save(game.addPlayer());
    }

    @Transactional
    public Game removePlayer(final Long gameId, final Long playerId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        // TODO: release player's cards
        return repository.save(game.removePlayerById(playerId));
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayers(final Long gameId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        // TODO: sum player's cards
        return game.getPlayers();
    }

    @Transactional(readOnly = true)
    public Map<Suit, Long> getSuits(final Long gameId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return game.getDecks().stream()
                .flatMap(d -> d.getCards().stream())
                .filter(Card::isOnDeck)
                .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
    }

    @Transactional(readOnly = true)
    public List<Card> getPlayerCards(final Long gameId, final Long playerId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return game.getDecks().stream()
                .flatMap(d -> d.getCards().stream())
                .filter(c -> c.isDealtToPlayer(playerId))
                .collect(Collectors.toList());
    }

}

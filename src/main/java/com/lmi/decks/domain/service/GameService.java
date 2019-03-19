package com.lmi.decks.domain.service;

import com.lmi.decks.domain.model.Deck;
import com.lmi.decks.domain.model.Game;
import com.lmi.decks.domain.model.Player;
import com.lmi.decks.domain.repository.DeckRepository;
import com.lmi.decks.domain.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return repository.save(game.removePlayerById(playerId));
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayers(final Long gameId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return game.getPlayers();
    }

}

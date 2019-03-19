package com.lmi.decks.domain.service;

import com.lmi.decks.domain.model.Deck;
import com.lmi.decks.domain.model.Game;
import com.lmi.decks.domain.repository.DeckRepository;
import com.lmi.decks.domain.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}

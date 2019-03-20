package com.lmi.decks.domain.service;

import com.lmi.decks.domain.model.*;
import com.lmi.decks.domain.repository.DeckRepository;
import com.lmi.decks.domain.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

    @Autowired
    private DeckRepository deckRepository;

    private final Random random = new Random();

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
        game.getCardStream()
                .filter(c -> c.isDealtToPlayer(playerId))
                .forEach(c -> c.setPlayerId(null));
        return repository.save(game.removePlayerById(playerId));
    }

    @Transactional
    public Game dealCard(final Long gameId, final Long playerId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        final Player player = game.getPlayers().stream().filter(p -> p.getId().equals(playerId)).findFirst()
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        final Optional<Card> card = game.getCardStream()
                .sorted(Comparator.comparingInt(Card::getPosition))
                .filter(Card::isOnDeck).findFirst();
        card.ifPresent(c -> c.setPlayerId(player.getId()));
        return repository.save(game);
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayers(final Long gameId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        game.getPlayers().forEach(p ->
            p.setTotal(game.getCardStream()
                    .filter(c -> c.isDealtToPlayer(p.getId()))
                    .mapToInt(Card::getHeight).sum())
        );
        return game.getPlayers().stream()
                .sorted(Comparator.comparingInt(Player::getTotal).reversed())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<Suit, Long> getSuits(final Long gameId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return game.getCardStream().filter(Card::isOnDeck)
                .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
    }

    @Transactional(readOnly = true)
    public List<Card> getCards(final Long gameId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return game.getCardStream().filter(Card::isOnDeck).sorted().collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Card> getPlayerCards(final Long gameId, final Long playerId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return game.getCardStream().filter(c -> c.isDealtToPlayer(playerId)).collect(Collectors.toList());
    }

    @Transactional
    public Game shuffle(final Long gameId) {
        final Game game = repository.findById(gameId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        game.getCardStream().forEach(c -> c.setPosition(random.nextInt()));
        return repository.save(game);
    }
}